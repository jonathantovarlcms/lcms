angular.module('AppMigracion', ['ngMaterial','mgo-angular-wizard'])
	.config(function($mdThemingProvider) {
})
.value('baseUrl', '/LCMS')
.controller('AppCtrl', function($scope, $interval, $http, $mdDialog, baseUrl, WizardHandler) {
	$scope.listaServidores=[];
	$scope.conectandoServidores=0;
	$scope.ajustarServidores=function(servidor) {
		if (servidor.origen) {
			var lSer = $scope.listaServidores;
			for(var i=0; i<lSer.length; i++){
				lSer[i].origen=false;
	    	}
			servidor.origen=true;
		}
	};
	$scope.validarServidores=function() {
		var lSer = $scope.listaServidores;
		var dat = [];
		var iOri = 0;
		var iDes = 0;
		for(var i=0; i<lSer.length; i++) {
			lSer[i].mensaje='';
			lSer[i].respuesta=false;
			lSer[i].validacion=false;
			if (lSer[i].origen) {
				iOri++;
				dat.push({
					id: lSer[i].id,
					origen: true,
					respuesta: false
				});
			} else {
				iDes++;
				dat.push({
					id: lSer[i].id,
					origen: false,
					respuesta: false
				});
			}
		}
		if (iOri==1 && iDes>0) {
			$scope.conectandoServidores = 1;
			for(var i=0; i<dat.length; i++) {
				var probar = function(ix) {
					$http({
						method: 'POST',
				        url: baseUrl+'/migracion/servidores/conectar',
				        data: dat[ix]
				    }).success(function (r) {
				    	dat[ix].respuesta=true;
				    	for(var i=0; i<lSer.length; i++){
				    		if (lSer[i].id==r.id) {
				    			lSer[i].mensaje=r.mensaje;
				    			lSer[i].respuesta=true;
				    			lSer[i].validacion=r.conectado;
				    			break;
				    		}
				    	}
				    	var c=true;
				    	for(var i=0; i<dat.length; i++){
				    		if (!dat[i].respuesta) {
				    			c=false;
				    			break;
				    		}
				    	}
				    	if (c) {
				    		c=true;
				    		for(var i=0; i<lSer.length; i++){
					    		if (!lSer[i].validacion) {
					    			c=false;
					    			break;
					    		}
					    	}
				    		$scope.conectandoServidores=2;
				    		if (c) {
				    			WizardHandler.wizard("Asistente").next();
				    			$scope.migrarServidores();
				    		}
				    	}
				    });
				}
				probar(i);
			}
		} else {
			$mdDialog.show(
				$mdDialog.alert()
		        	.clickOutsideToClose(true)
		        	.title('Error en la configuración')
		        	.textContent('Debe seleccionar un servidor de origen.')
		        	.ok('Aceptar')
		    );
		}
	};
	$scope.visualizarErrorConexion=function(servidor) {
		if (!servidor.validacion) {
			$mdDialog.show(
				$mdDialog.alert()
		        	.clickOutsideToClose(true)
		        	.title('Error en la conexión')
		        	.textContent(servidor.mensaje)
		        	.ok('Aceptar')
		    );
		}
	};
	$scope.migrarServidores=function() {
		var lSer = $scope.listaServidores;
		var sOri;
		var sDes = [];
		for(var i=0; i<lSer.length; i++) {
			if (lSer[i].origen) {
				sOri=lSer[i];
			} else {
				sDes.push(lSer[i]);
			}
		}
		var dat={servidorOrigen: sOri, servidoresDestino: sDes};
		$http({
			method: 'POST',
	        url: baseUrl+'/migracion/servidores/migrar',
	        data:dat 
	    }).success(function (r) {
	    	alert('Aqui');
	    });
	};
	$http({
		method: 'GET',
        url: baseUrl+'/migracion/servidores'
    }).success(function (r) {
    	for(var i=0; i<r.length; i++){
    		r[i].origen=false;
    		r[i].mensaje='';
    		r[i].respuesta=false;
    		r[i].validacion=false;
    	}
    	$scope.listaServidores = r;
    });
});
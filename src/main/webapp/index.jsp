<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" >
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="./node_modules/angular-material/angular-material.min.css">
  <link rel="stylesheet" href="./node_modules/angular-wizard/dist/angular-wizard.min.css">
  <link rel="stylesheet" href="./lcms/css/index.css">
</head>
<body ng-app="AppMigracion" ng-cloak>
	<div ng-controller="AppCtrl as vm" layout="column" layout-margin ng-cloak>
		<md-content>
			<wizard name="Asistente" on-finish="finishedWizard()"> 
			    <wz-step wz-title="Servidores">
			        <h1>Servidores</h1>
			        <md-subheader class="md-no-sticky">Seleccione el servidor de origen y los servidores de destino para realizar la migración:</md-subheader>
			        <md-list>
		               	<md-list-item class="md-2-line" ng-repeat="(index, servidor) in listaServidores" ng-click="null"> 
							<img ng-src="./lcms/img/{{servidor.tipo}}.svg" class="md-avatar" alt="{{servidor.tipo}}" />
		                	<div class="md-list-item-text compact">
		                     	<h3>{{servidor.servidor}}</h3>
		                     	<p>{{servidor.baseDatos}}</p>
		                  	</div>
		                  	<md-button class="md-icon-button" aria-label="Validación" ng-hide="!servidor.respuesta" ng-click="visualizarErrorConexion(servidor)">
        						<md-icon md-svg-icon="./lcms/img/{{servidor.validacion ? 'success':'error'}}.svg"></md-icon>
      						</md-button>
		                  	<md-switch ng-model="servidor.origen" aria-label="Origen o Destino" ng-change="ajustarServidores(servidor)" ng-hide="conectandoServidores==1" style="width:100px">
		                  		{{servidor.origen ? 'Origen' : 'Destino'}}
  							</md-switch>
  							<md-progress-circular md-mode="indeterminate" md-diameter="40" class="md-warn md-hue-3" ng-hide="servidor.respuesta || conectandoServidores!=1"></md-progress-circular>
						  	<md-divider ng-if="!$last"></md-divider>
		               	</md-list-item>
		            </md-list>
		            <div style="text-align:right;">
		            	<md-button class="md-raised md-primary" ng-click="validarServidores()" ng-disabled="conectandoServidores==1">Siguiente</md-button>
		            </div>
			    </wz-step>
			    <wz-step wz-title="Migración">
			        <h1>Migración</h1>
			        <md-progress-linear md-mode="determinate" value="{{vm.determinateValue}}"></md-progress-linear>
			        <p>You have continued here!</p>
			        <md-button class="md-raised md-primary" wz-previous>Anterior</md-button>
			        <div style="float:right;">
		            	<md-button class="md-raised md-primary" wz-next>Siguiente</md-button>
		            </div>
			    </wz-step>
			    <wz-step wz-title="Verificación">
			    	<h1>Verificación</h1>
			        <p>Even more steps!!</p>
			        <input type="submit" wz-next value="Finish now" />
			    </wz-step>
			</wizard>
		</md-content>
	</div>
  
	<!-- Angular Material requires Angular.js Libraries -->
	<script type="text/javascript" src="./node_modules/angular/angular.min.js"></script>
	<script type="text/javascript" src="./node_modules/angular-animate/angular-animate.min.js"></script>
	<script type="text/javascript" src="./node_modules/angular-aria/angular-aria.min.js"></script>
	<script type="text/javascript" src="./node_modules/angular-messages/angular-messages.min.js"></script>
	<script type="text/javascript" src="./node_modules/angular-wizard/dist/angular-wizard.min.js"></script>

	<!-- Angular Material Library -->
	<script type="text/javascript" src="./node_modules/angular-material/angular-material.min.js"></script>
  
	<!-- Application bootstrap  -->
	<script type="text/javascript" src="./lcms/js/index.js"></script>
</body>
</html>
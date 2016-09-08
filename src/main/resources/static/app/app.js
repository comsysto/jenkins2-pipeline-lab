angular.module('characterViewer', [
  'ngMaterial',
  'characterViewer.startpage',
  'characterViewer.character.sheet',
  'characterViewer.navigation'])

  .config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
      .primaryPalette('purple')
      .accentPalette('orange')
  })

  .controller('AppController', ['$mdSidenav', AppController])

;

AppController.$routeConfig = [
  {path: '/', redirectTo: '/characters'},

  {path: '/characters', components: {content: 'startpage', navigation: 'navigation'}},
  {path: '/characters/:characterId', components: {content: 'characterSheet', navigation: 'navigation'}}
];

function AppController($mdSidenav) {
  var ctrl = this;

  ctrl.toggleSidenav = function (id) {
    $mdSidenav(id).toggle();
  };
}



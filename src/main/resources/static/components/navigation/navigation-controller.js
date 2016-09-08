angular.module('characterViewer.navigation', [
  'ngMaterial',
  'ngNewRouter',
  'charViewer.character.service'])
  .controller('NavigationController', ['$router', '$mdSidenav', 'CharacterService', NavigationController]);

function NavigationController($router, $mdSidenav, CharacterService) {
  var ctrl = this;

  ctrl.characters = CharacterService.getAll();

  ctrl.navigateTo = function (character) {
    $mdSidenav('left')
      .close()
      .then(function () {
        $router.parent.navigate('/characters/' + character.id);
      });
  };
}

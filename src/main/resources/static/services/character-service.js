angular.module('charViewer.character.service', [
  'ngResource'
])

  .service('CharacterService', ['$resource', CharacterService]);

function CharacterService($resource) {
  /*
   * TODO implement caching
   * The service should probably implement some kind of caching. We should avoid loading the character with each method
   * call instead we should have a cache, that stores the characters. This could be as simple as some kind of Map
   * storing ids and already loaded character.
   */

  var service = this;
  var Character = $resource('/characters/:charId', {charId: '@id'})

  service.getAll = function () {
    return Character.query();
  };

  service.getById = function (id) {
    return Character.get({charId: id});
  };
}

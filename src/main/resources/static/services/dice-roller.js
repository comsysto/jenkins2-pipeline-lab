angular.module('charViewer.diceRoller.service', [])

  .service('DiceRollerService', [DiceRollerService]);

function DiceRollerService() {
  /*
   * TODO get fancy with 3D dice
   * It would be quite fancy to have the die roll around on screen.
   */

  var service = this;

  service.d20 = function () {
    return rollDie(20);
  };

  var rollDie = function (die) {
    return Math.floor(Math.random() * die) + 1;
  };
}

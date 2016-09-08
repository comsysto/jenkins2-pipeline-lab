angular.module('charViewer.addPlus', [])

  .filter('addPlus', AddPlus);

function AddPlus() {
  return function (input) {
    if (!isNaN(input) && input > 0) {
      return '+' + input;
    } else {
      return input;
    }
  }
}

angular.module('charViewer.attribute.modifier.service', [])

  .service('AttributeModifierService', [AttributeModifierService]);

function AttributeModifierService() {
  var service = this;

  service.calcModifier = function (attributeValue) {
    return Math.floor((attributeValue - 10) / 2);
  };
}

angular.module('charViewer.attribute.modifier.filter', [
  'charViewer.attribute.modifier.service'
])

  .filter('attributeModifier', ['AttributeModifierService', AttributeModifier]);

function AttributeModifier(AttributeModifierService) {
  return function (input) {
    return AttributeModifierService.calcModifier(input);
  }
}

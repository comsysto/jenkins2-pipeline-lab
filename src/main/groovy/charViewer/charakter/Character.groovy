package charViewer.charakter

import static java.util.UUID.randomUUID
import static java.util.stream.Collectors.summingInt

class Character {

  final String id = randomUUID().toString()

  String name
  String icon = "fa-user"

  List<CharacterClass> classes = []

  Map<Attribute, Integer> attributes = [:]

  List<String> skillProficiency = []

  int getProficiencyBonus() {
    final int effectiveLevel = classes.stream()
      .collect(summingInt { it.level })

    return 2 + (effectiveLevel - 1) / 4
  }
}

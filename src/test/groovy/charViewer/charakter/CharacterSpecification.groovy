package charViewer.charakter

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CharacterSpecification extends Specification {

  def 'proficiency bonus for level #level is #bonus'() {
    given:
    def character = new Character(
      classes: [
        new CharacterClass(
          name: 'test class',
          level: level
        )
      ]
    )

    expect:
    character.proficiencyBonus == bonus

    where:
    level || bonus
    1     || 2
    2     || 2
    3     || 2
    4     || 2
    5     || 3
    6     || 3
    7     || 3
    8     || 3
    9     || 4
    9     || 4
    10    || 4
    11    || 4
    12    || 4
    13    || 5
    14    || 5
    15    || 5
    16    || 5
    17    || 6
    18    || 6
    19    || 6
    20    || 6
  }
}

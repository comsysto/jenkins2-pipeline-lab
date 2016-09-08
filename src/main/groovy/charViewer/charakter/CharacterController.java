package charViewer.charakter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/characters", produces = APPLICATION_JSON_VALUE)
public class CharacterController {

  private final CharacterRepository characterRepository;

  @Autowired
  public CharacterController(CharacterRepository characterRepository) {
    this.characterRepository = characterRepository;
  }

  @RequestMapping(method = GET)
  public ResponseEntity<List<Character>> getAll() {
    final List<Character> characters = characterRepository.findAll();

    return new ResponseEntity<>(characters, OK);
  }

  @RequestMapping(value = "/{id}")
  public ResponseEntity<Character> getById(@PathVariable String id) {
    final Optional<Character> character = characterRepository.findById(id);

    if (character.isPresent()) {
      return new ResponseEntity<>(character.get(), OK);
    } else {
      return new ResponseEntity<>(NOT_FOUND);
    }
  }
}

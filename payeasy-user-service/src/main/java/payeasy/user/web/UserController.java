package payeasy.user.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payeasy.user.entity.User;
import payeasy.user.repo.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserRepository repo;
  public UserController(UserRepository repo){ this.repo = repo; }

  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody User u){
    return ResponseEntity.ok(repo.save(u));
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> one(@PathVariable Long id){
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<User> all(@RequestParam(required=false) String mobile,
                        @RequestParam(required=false) String email){
    if(mobile!=null) return repo.findByMobile(mobile).map(List::of).orElse(List.of());
    if(email!=null) return repo.findByEmail(email).map(List::of).orElse(List.of());
    return repo.findAll();
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(@RequestHeader Long id, @RequestBody User incoming){
    return repo.findById(id).map(u -> {
      if(incoming.getFullName()!=null) u.setFullName(incoming.getFullName());
      if(incoming.getEmail()!=null) u.setEmail(incoming.getEmail());
      if(incoming.getMobile()!=null) u.setMobile(incoming.getMobile());
      if(incoming.getPasswordHash()!=null) u.setPasswordHash(incoming.getPasswordHash());
      if(incoming.getMfaEnabled()!=null) u.setMfaEnabled(incoming.getMfaEnabled());
      return ResponseEntity.ok(repo.save(u));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    if(repo.existsById(id)) { repo.deleteById(id); return ResponseEntity.noContent().build();}
    return ResponseEntity.notFound().build();
  }
}

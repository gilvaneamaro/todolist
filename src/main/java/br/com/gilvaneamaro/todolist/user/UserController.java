package br.com.gilvaneamaro.todolist.user;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository repository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){

        var user = this.repository.findByUsername(userModel.getUsername());
        if(user != null) {
            System.out.println("Usuário já existe!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }


        var passwordHashred = BCrypt.withDefaults().hashToString(12,userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        var userCreated = this.repository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created!");
    }

}

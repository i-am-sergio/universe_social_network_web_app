package com.unsa.backend.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<UserModel> getUsers() {
        // UserModel user = userService.obtenerUsuarios().get(0);
        // System.out.println(user.getFirstname());
        return userService.getUsers();
    }

    // SOLO 1 USUARIO
    @GetMapping("/{id}")
    public UserModel getUser(@PathVariable Long id) {

        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel updatedUser) {
        UserModel updated = userService.updateUser(id, updatedUser);
        if (updated != null) {
            return ResponseEntity.ok(updated); // Código 200: Actualización exitosa.
        } else {
            return ResponseEntity.notFound().build(); // Código 404: Usuario no encontrado.
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserModel deletedUser = userService.deleteUser(id);

        if (deletedUser != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Código 204: Recurso eliminado con éxito.
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Código 404: Recurso no encontrado.
        }
    }

    // @PostMapping("/{id}/follow")
    // public UserModel followUser(@PathVariable Long id, @RequestBody
    // UserFollowRequest followRequest) {
    // Long followerId = followRequest.getFollowerId();
    // return userService.followUser(id, followerId);
    // }

    // @PostMapping("/{id}/unfollow")
    // public UserModel unfollowUser(@PathVariable Long id, @RequestBody
    // UserUnfollowRequest unfollowRequest) {
    // Long unfollowerId = unfollowRequest.getUnfollowerId();
    // return userService.unfollowUser(id, unfollowerId);
    // }

}

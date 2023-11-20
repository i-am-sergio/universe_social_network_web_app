package com.unsa.backend.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    
    final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getUser(){
        return ResponseEntity.ok(userService.getUsers());
    }

    // SOLO 1 USUARIO
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
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

    @PutMapping("/{id}/follow")
    public ResponseEntity<String> followUser(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long followerId = ((UserModel) userDetails).getId();
            userService.followUser(followerId, id);
            return new ResponseEntity<>("User followed!", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Manejar la excepción cuando no se encuentra el usuario
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/unfollow")
    public ResponseEntity<String> unfollowUser(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long followerId = ((UserModel) userDetails).getId();
            userService.unfollowUser(followerId, id);
            return new ResponseEntity<>("User unfollowed!", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.unsa.backend.messages;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<ChatModel, Long> {
    /*List<ChatModel> findByMembersContaining(Long members);
    
    @Query("SELECT c FROM ChatModel c WHERE :firstUserId IN c.members AND :secondUserId IN c.members")
    ChatModel findByMembers(String firstUserId, String secondUserId);*/
}

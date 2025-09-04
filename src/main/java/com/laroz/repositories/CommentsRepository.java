package com.laroz.repositories;

import com.laroz.models.Comments;
import com.laroz.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}

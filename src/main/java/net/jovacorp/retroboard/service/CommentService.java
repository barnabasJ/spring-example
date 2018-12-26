package net.jovacorp.retroboard.service;

import net.jovacorp.retroboard.model.Comment;
import net.jovacorp.retroboard.repo.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {
  private static Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

  private final CommentRepository commentRepository;

  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  public List<Comment> saveAll(List<Comment> comments) {
    LOGGER.info("Saving {}", comments);
    return commentRepository.saveAll(comments);
  }

  public List<Comment> getAllCommentsForToday() {
    LocalDate localDate = LocalDate.now();
    return commentRepository.findByCreatedYearAndMonthAndDay(
        localDate.getYear(), localDate.getMonth().getValue(), localDate.getDayOfMonth());
  }
}

package net.jovacorp.retroboard.service;

import net.jovacorp.retroboard.model.Comment;
import net.jovacorp.retroboard.model.CommentType;
import net.jovacorp.retroboard.repo.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommentServiceTest {

  @MockBean private CommentRepository commentRepository;

  private CommentService commentService;

  @Before
  public void setup() {
    commentService = new CommentService(commentRepository);
  }

  @Test
  public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
    // Given
    Comment comment = new Comment();
    comment.setComment("Test");
    comment.setType(CommentType.PLUS);
    comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    List<Comment> comments = Arrays.asList(comment);
    LocalDate now = LocalDate.now();

    when(commentRepository.findByCreatedYearAndMonthAndDay(
            now.getYear(), now.getMonth().getValue(), now.getDayOfMonth()))
        .thenReturn(comments);

    // When
    List<Comment> actualComments = commentService.getAllCommentsForToday();

    // Then
    verify(commentRepository, times(1))
        .findByCreatedYearAndMonthAndDay(
            now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());
    assertThat(comments).isEqualTo(actualComments);
  }

  @Test
  public void saveAll_HappyPath_ShouldSave2Comments() {
    // Given
    Comment comment = new Comment();
    comment.setComment("Test Plus");
    comment.setType(CommentType.PLUS);
    comment.setCreatedBy("Shazin");
    comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

    Comment comment2 = new Comment();
    comment.setComment("Test Star");
    comment.setType(CommentType.STAR);
    comment.setCreatedBy("Shahim");
    comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

    List<Comment> comments = Arrays.asList(comment, comment2);

    when(commentRepository.saveAll(comments)).thenReturn(comments);

    // When
    List<Comment> saved = commentService.saveAll(comments);

    // Then
    verify(commentRepository, times(1)).saveAll(comments);
    assertThat(saved).isNotEmpty();
  }
}

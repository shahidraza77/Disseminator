package com.revature.disseminator.project.service;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.revature.disseminator.project.dto.PostRequest;
import com.revature.disseminator.project.dto.PostResponse;
import com.revature.disseminator.project.mapper.PostMapper;
import com.revature.disseminator.project.model.Post;
import com.revature.disseminator.project.model.Subreddit;
import com.revature.disseminator.project.model.User;
import com.revature.disseminator.project.repository.PostRepository;
import com.revature.disseminator.project.repository.SubredditRepository;
import com.revature.disseminator.project.repository.UserRepository;



class PostServiceTest {

private final PostRepository postRepository= Mockito.mock(PostRepository.class);
   private final SubredditRepository subredditRepository= Mockito.mock(SubredditRepository.class);
   private final UserRepository userRepository = Mockito.mock(UserRepository.class);
   
   private final PostMapper postMapper= Mockito.mock(PostMapper.class);
   private final AuthService authService= Mockito.mock(AuthService.class);
@Captor
private ArgumentCaptor<Post> postArgumentCaptor;
@Test
@DisplayName("Find a post by id")
void testFindById() {

PostService postService = new PostService(postRepository,subredditRepository,userRepository,authService,postMapper);
Post post = new Post(3L,"First Post", "https://www.youtube.com","First post test",3,null,Instant.now(),null);
PostResponse expectedPostResponse = new PostResponse(3L,"First Post","https://www.youtube.com","First post test","testUser","First SubReddit",0,0,"1 Hour ago",false,false);

Mockito.when(postRepository.findById(3L)).thenReturn(Optional.of(post));
Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

PostResponse actualPostResponse = postService.getPost(3L);

Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
Assertions.assertThat(actualPostResponse.getPostName()).isEqualTo(expectedPostResponse.getPostName());

}
@Test
@DisplayName("Save Posts")
public void shouldSavePosts() {
ArrayList<Post> emptyListposts=new ArrayList<>();
PostService postService= new PostService(postRepository, subredditRepository, userRepository, authService, postMapper);
User currentUser = new User(4L,"hema", "hema", "hema@gmail.com", Instant.now(),true);
Subreddit subreddit= new Subreddit(4L,"Spring-Boot", "Spring Security", emptyListposts,Instant.now(),currentUser);
Post post=new Post(4L,"First Post", "https://spring.io/projects/spring-security","Description",4,null,Instant.now(),null);
PostRequest postRequest= new PostRequest(null,"First Subreddit", "First Post","https://www.youtube.com","First post test");
Mockito.when(subredditRepository.findByName("First subreddit"))
.thenReturn(Optional.of(subreddit));
Mockito.when(postMapper.map(postRequest, subreddit, currentUser))
.thenReturn(post);
Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
postService.save(postRequest);
Mockito.verify(postRepository,Mockito.times(1)).save(postArgumentCaptor.capture());
Assertions.assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(4L);
Assertions.assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo("First Post");

}

}
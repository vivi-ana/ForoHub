package alura.challenge.ForoHub.controller;

import alura.challenge.ForoHub.domain.topic.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * REST controller for managing topics.
 */
@RestController
@RequestMapping("topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    TopicService topicService;

    /**
     * Registers a new topic.
     * @param topicRegister the topic details for registration
     * @param uriComponentsBuilder for building the URI of the created topic
     * @return a ResponseEntity with the created topic details
     */
    @PostMapping
    @Transactional
    public ResponseEntity<TopicDetail> registerTopic(@RequestBody @Valid TopicRegister topicRegister, UriComponentsBuilder uriComponentsBuilder) {
        var topicDetail = topicService.registerTopic(topicRegister);
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicDetail.id()).toUri();
        return ResponseEntity.created(url).body(topicDetail);
    }

    /**
     * Retrieves a paginated list of topics, optionally filtered by course name and year.
     * @param pageable the pagination and sorting information * @param courseName the optional course name to filter topics
     * @param year the optional year to filter topics * @return a ResponseEntity with the paginated list of topics
     */
    @GetMapping
    public ResponseEntity<Page<TopicList>> topicList(@PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable,
                                                     @RequestParam(required = false) String courseName, @RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(topicService.getAll(pageable, courseName, year));
    }

    /**
     * Updates an existing topic.
     * @param topicUpdate the updated topic details
     * @param id the id of the topic to be updated
     * @return a ResponseEntity with the updated topic details
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity topicUpdate(@RequestBody @Valid TopicUpdate topicUpdate, @PathVariable Long id) {
        return ResponseEntity.ok(topicService.updateTopic(topicUpdate, id));
    }

    /**
     * Finds a topic by its id.
     * @param id the id of the topic to be found
     * @return a ResponseEntity with the topic details
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDetail> findTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    /**
     * Deletes a topic by its id.
     * @param id the id of the topic to be deleted
     * @return a ResponseEntity indicating no content
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity topicDelete(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
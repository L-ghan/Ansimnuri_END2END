package com.end2end.ansimnuri.note.controller;

import com.end2end.ansimnuri.note.dto.NoteDTO;
import com.end2end.ansimnuri.note.dto.NoteRecDTO;
import com.end2end.ansimnuri.note.dto.NoteReplyDTO;
import com.end2end.ansimnuri.note.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="쪽지 API", description = "쪽지 CRUD, 쪽지 추천, 쪽지 답글 CRUD 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/note")
@RestController
public class NoteController {
    private final NoteService noteService;
    private final NoteRecService noteRecService;
    private final NoteReplyService noteReplyService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> selectAll() {
        return ResponseEntity.ok(noteService.selectAll());
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody NoteDTO noteDTO) {
        noteService.insert(noteDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody NoteDTO noteDTO) {
        noteService.update(noteDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable long id) {
        noteService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recommend")
    public ResponseEntity<Void> insert(@RequestBody NoteRecDTO dto) {
        noteRecService.insert(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/recommend")
    public ResponseEntity<Void> delete(@RequestBody NoteRecDTO dto) {
        noteRecService.delete(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reply/{noteId}")
    public ResponseEntity<List<NoteReplyDTO>> selectByNoteId(@PathVariable Long noteId) {
        return ResponseEntity.ok(noteReplyService.selectByNoteId(noteId));
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> insert(@RequestBody NoteReplyDTO noteReplyDTO) {
        noteReplyService.insert(noteReplyDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reply")
    public ResponseEntity<Void> update(@RequestBody NoteReplyDTO noteReplyDTO) {
        noteReplyService.update(noteReplyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reply/{id}")
    public ResponseEntity<Void> deleteReplyById(@PathVariable long id) {
        noteReplyService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

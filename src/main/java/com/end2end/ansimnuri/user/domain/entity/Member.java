package com.end2end.ansimnuri.user.domain.entity;

import com.end2end.ansimnuri.board.domain.entity.Qna;
import com.end2end.ansimnuri.message.domain.entity.Message;
import com.end2end.ansimnuri.message.domain.entity.MessageBlock;
import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.note.domain.entity.NoteRec;
import com.end2end.ansimnuri.note.domain.entity.NoteReply;
import com.end2end.ansimnuri.user.dto.UserDTO;
import com.end2end.ansimnuri.util.entitly.Timestamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SequenceGenerator(
        name = "userSequenceGenerator",
        sequenceName = "USER_ID_SEQ",
        allocationSize = 1
)
@Table(name = "MEMBER")
@Entity
public class Member extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    private Long id;
    @Column(name = "LOGIN_ID", nullable = false, unique = true)
    private String loginId;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "POSTCODE", nullable = false)
    private String postcode;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "DETAIL_ADDRESS", nullable = false)
    private String detailAddress;

    @OneToMany(mappedBy = "member")
    private List<Qna> qnaList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Block> blockList;
    @OneToMany(mappedBy = "reporter", orphanRemoval = true)
    private List<Complaint> complaintReporterList;
    @OneToMany(mappedBy = "reportee", orphanRemoval = true)
    private List<Complaint> complaintReporteeList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Message> messageList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<MessageBlock> messageBlockList;
    @OneToMany(mappedBy = "messageBlock", orphanRemoval = true)
    private List<MessageBlock> messageBlockedList;
    @OneToMany(mappedBy = "member")
    private List<Note> noteList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<NoteRec> noteRecList;
    @OneToMany(mappedBy = "member")
    private List<NoteReply> noteReplyList;

    public static Member of (UserDTO userDTO) {
        return Member.builder()
                .id(userDTO.getId())
                .build();
    }

    public void update(UserDTO userDTO) {
    }
}

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    UserController(JWTUtil jwtUtil,
                   UserService userService,
                   AuthenticationManager authenticationManager,
                   PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable long userId, @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.updateUser(userId, userRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(),
                    loginRequestDTO.getPassword()
                    ));
            return jwtUtil.generateToken(loginRequestDTO.getUsername());
        } catch (Exception e) {
            return "Username or password is wrong";
        }
    }
}

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;
    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@RequestParam long userId,
                                                      @RequestBody NoteRequestDTO noteRequestDTO) {
        return new ResponseEntity<>(noteService.create(userId, noteRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@PathVariable long noteId) {
        return new ResponseEntity<>(noteService.getNoteById(noteId), HttpStatus.OK);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> update(@PathVariable long noteId,
                                                  @RequestBody NoteRequestDTO noteRequestDTO) {
        return new ResponseEntity<>(noteService.update(noteId, noteRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable long noteId) {
        noteService.delete(noteId);
    }

    @DeleteMapping("/{noteId}/deleteTag")
    public ResponseEntity<NoteResponseDTO> deleteTag(@PathVariable long noteId,
                          @RequestParam String tagName) {
        return new ResponseEntity<>(noteService.deleteTag(noteId, tagName), HttpStatus.OK);
    }

    @PutMapping("/{noteId}/addTag")
    public ResponseEntity<NoteResponseDTO> addTag(@PathVariable long noteId,
                                                  @RequestBody AddTagRequestDTO addTagRequestDTO) {
        return new ResponseEntity<>(noteService.addTag(noteId, addTagRequestDTO.getTagName()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> filterNotes(
            @RequestParam long userId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) List<String> tag
    ) {
        return new ResponseEntity<>(noteService.filter(userId, title, content, tag), HttpStatus.OK);
    }
}

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;
    TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagResponseDTO>> findAll(@RequestParam int page,
                                                        @RequestParam int size) {
        return new ResponseEntity<>(tagService.allTags(page, size), HttpStatus.OK);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteSummaryDTO>> findNotesByTag(@RequestParam String tagName,
                                                               @RequestParam int page,
                                                               @RequestParam int size) {
        return new ResponseEntity<>(tagService.getNotesByTag(tagName, page, size), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagResponseDTO>> searchTags(@RequestParam String keyword) {
        return new ResponseEntity<>(tagService.searchTags(keyword), HttpStatus.OK);
    }
}

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = UserMapper.toEntity(userRequestDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Roles.USER);
        return UserMapper.toDTO(userRepo.save(userEntity));
    }

    public UserResponseDTO updateUser(long userId, UserRequestDTO userRequestDTO) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setEmail(userRequestDTO.getEmail());
        return UserMapper.toDTO(userRepo.save(userEntity));
    }

    public void deleteUser(long userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
        } else {
            throw new RuntimeException("No matching userId found");
        }
    }

    public UserResponseDTO getUser(long userId) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No Matching userId found"));
        return UserMapper.toDTO(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepo.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}

@Service
public class NoteService {

    private final NoteRepo noteRepo;
    private final UserRepo userRepo;
    private final TagRepo tagRepo;
    NoteService(NoteRepo noteRepo,
                UserRepo userRepo,
                TagRepo tagRepo) {
       this.noteRepo = noteRepo;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
    }

    public NoteResponseDTO create(long userId, NoteRequestDTO noteRequestDTO) {
        UserEntity userEntity = userRepo.findById(userId)
                        .orElseThrow(() -> new RuntimeException("No matching userId found"));
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(noteRequestDTO.getTitle());
        noteEntity.setContent(noteRequestDTO.getContent());
        noteEntity.setUserEntity(userEntity);
        userEntity.getNotesEntityList().add(noteEntity);
        if (noteRequestDTO.getTagList() != null && !noteRequestDTO.getTagList().isEmpty()) {
            noteRequestDTO.getTagList()
                    .forEach(tag -> {
                        TagEntity tagEntity = tagRepo.findByTagName(tag)
                                .orElseGet(() -> {
                                    TagEntity newTag = new TagEntity();
                                    newTag.setTagName(tag);
                                    return tagRepo.save(newTag);
                                });
                        NoteTagEntity noteTag = new NoteTagEntity();
                        noteTag.setNoteEntity(noteEntity);
                        noteTag.setTagEntity(tagEntity);
                        noteEntity.getNoteTagEntityList().add(noteTag);
                        tagEntity.getNoteTagEntityList().add(noteTag);
                    });
        }
        return NoteMapper.toDTO(noteRepo.save(noteEntity));
    }

    public NoteResponseDTO update(long noteId, NoteRequestDTO noteRequestDTO) {
       NoteEntity noteEntity = noteRepo.findById(noteId)
               .orElseThrow(() -> new RuntimeException("No matching notesId found"));
       noteEntity.setTitle(noteRequestDTO.getTitle());
       noteEntity.setContent(noteRequestDTO.getContent());
       return NoteMapper.toDTO(noteRepo.save(noteEntity));
    }

    public void delete(long noteId) {
        if (noteRepo.existsById(noteId)) {
            noteRepo.deleteById(noteId);
        } else {
            throw new RuntimeException("No matching notesId found");
        }
    }

    public NoteResponseDTO getNoteById(long noteId) {
        NoteEntity noteEntity = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("No matching notesId found"));
        return NoteMapper.toDTO(noteEntity);
    }

    public NoteResponseDTO deleteTag(long noteId, String tagName) {
        NoteEntity noteEntity = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("No matching noteId found"));
        TagEntity tagEntity = tagRepo.findByTagName(tagName)
                .orElseThrow(() ->  new RuntimeException("No matching tag found"));
        NoteTagEntity toRemove = noteEntity.getNoteTagEntityList()
                .stream()
                .filter(noteTag -> noteTag.getTagEntity().getTagId().equals(tagEntity.getTagId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tag not associated with this note"));
        noteEntity.getNoteTagEntityList().remove(toRemove);
        tagEntity.getNoteTagEntityList().remove(toRemove);
        noteRepo.save(noteEntity);
        return NoteMapper.toDTO(noteEntity);
    }

    public NoteResponseDTO addTag(long noteId, String tagName) {
        NoteEntity noteEntity = noteRepo.findById(noteId)
                .orElseThrow(() -> new RuntimeException("No matching noteId found"));
        TagEntity tagEntity = tagRepo.findByTagName(tagName)
                .orElseGet(() -> {
                    TagEntity newTag = new TagEntity();
                    newTag.setTagName(tagName);
                    return tagRepo.save(newTag);
                });
        boolean alreadyExist = noteEntity.getNoteTagEntityList()
                .stream()
                .anyMatch(noteTag -> noteTag.getTagEntity().getTagName().equals(tagEntity.getTagName()));
        if (alreadyExist) {
            return NoteMapper.toDTO(noteEntity);
        }
        NoteTagEntity noteTag = new NoteTagEntity();
        noteTag.setNoteEntity(noteEntity);
        noteTag.setTagEntity(tagEntity);
        noteEntity.getNoteTagEntityList().add(noteTag);
        tagEntity.getNoteTagEntityList().add(noteTag);
        return NoteMapper.toDTO(noteRepo.save(noteEntity));
    }

    public List<NoteResponseDTO> filter(long userId,
                                  String title,
                                  String content,
                                  List<String> tag) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("No user found"));

        Stream<NoteEntity> stream = userEntity.getNotesEntityList().stream();

        if (title != null && !title.isBlank()) {
            stream = stream.filter(
                    noteEntity -> noteEntity.getTitle()
                            .toLowerCase()
                            .contains(title.toLowerCase())
            );
        }

        if (content != null && !content.isBlank()) {
            stream = stream.filter(
                    noteEntity -> noteEntity.getContent()
                            .toLowerCase()
                            .contains(content.toLowerCase())
            );
        }

        if (tag != null && !tag.isEmpty()) {
            stream = stream.filter(
                    noteEntity -> noteEntity.getNoteTagEntityList()
                            .stream()
                            .map(noteTag -> noteTag.getTagEntity().getTagName())
                            .collect(Collectors.toSet())
                            .containsAll(tag)
            );
        }

        return stream
                .map(noteEntity -> NoteMapper.toDTO(noteEntity))
                .toList();
    }
}

@Service
public class TagService {
private final TagRepo tagRepo;
private final NoteTagRepo noteTagRepo;
TagService(TagRepo tagRepo,
NoteTagRepo noteTagRepo) {
this.tagRepo = tagRepo;
this.noteTagRepo = noteTagRepo;
}

    public List<TagResponseDTO> allTags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tagRepo.findAll(pageable)
                .getContent()
                .stream()
                .map(tagEntity -> TagMapper.toDTO(tagEntity))
                .toList();
    }

    public List<NoteSummaryDTO> getNotesByTag(String tagName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        TagEntity tagEntity = tagRepo.findByTagName(tagName)
                .orElseThrow(() -> new RuntimeException("No tag found"));
        return noteTagRepo.findByTagEntity(tagEntity, pageable)
                .getContent()
                .stream()
                .map(noteTag -> NoteSummaryMapper.toDTO(noteTag.getNoteEntity()))
                .toList();
    }

    public List<TagResponseDTO> searchTags(String keyword) {
        return tagRepo.findByTagNameContainingIgnoreCase(keyword)
                .stream()
                .map(tagEntity -> TagMapper.toDTO(tagEntity))
                .toList();
    }
}

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
Optional<UserEntity> findByUsername(String username);
}

@Repository
public interface NoteRepo extends JpaRepository<NoteEntity, Long> {
}

@Repository
public interface TagRepo extends JpaRepository<TagEntity, Long> {
Optional<TagEntity> findByTagName(String tagName);
Optional<TagEntity> findByTagNameContainingIgnoreCase(String keyword);
}

@Repository
public interface NoteTagRepo extends JpaRepository<NoteTagEntity, Long> {
Page<NoteTagEntity> findByTagEntity(TagEntity tag, Pageable pageable);
}

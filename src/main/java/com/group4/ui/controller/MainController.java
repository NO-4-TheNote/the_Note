package com.group4.ui.controller;

import com.group4.dao.ICatalogDao;
import com.group4.dao.IKnowledgeBaseDao;
import com.group4.domain.Catalog;
import com.group4.domain.KnowledgeBase;
import com.group4.domain.Note;
import com.group4.service.ICatalogService;
import com.group4.service.IKnowledgeBaseService;
import com.group4.service.INoteService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

/**
 * 主要是写一些事件调用的方法
 */
@Component
public class MainController implements Initializable {

    private Map<String,Integer> kBNameKBIdMap =new HashMap<String, Integer>();
    private Integer curKBId;
    private KnowledgeBase curKB;
    private Map<String,Integer> catalogNameCatalogIdMap=new HashMap<String, Integer>();
    private Integer curCatalogId;
    private Catalog curCatalog;
    private Map<String,Integer> noteNameNoteIdMap=new HashMap<String,Integer>();
    private Integer curNoteId;
    private Note curNote;

    @FXML
    private HTMLEditor content;
    @FXML
    private Menu open;
    @FXML
    private WebView webView;
    private final String mainUrl = getClass().getResource("/editor/index.html").toExternalForm();
    private WebEngine webEngine;
    @FXML
    private javafx.scene.control.TextField title;
    @FXML
    private javafx.scene.control.TextField searchTextField; // todo: search
    @FXML
    private Button searchButton;
    @FXML
    private ListView<String> catalogList;
    @FXML
    private ListView<String> noteList;

    @Autowired
    private IKnowledgeBaseService knowledgeBaseService;

    @Autowired
    private ICatalogService catalogService;

    @Autowired
    private INoteService noteService;

    @Autowired
    private IKnowledgeBaseDao knowledgeBaseDao;
    @Autowired
    private ICatalogDao catalogDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        open.setOnShowing(evt->{
            initOpenMenu();
        });

        webEngine = webView.getEngine();
        webEngine.load(mainUrl);
        webEngine.setJavaScriptEnabled(true);

        initCatalogListMenu();
        initNoteListMenu();
    }


    /**
     * 在open菜单项显示可以
     * 打开的知识库
     */
    public void initOpenMenu(){

        List<MenuItem> kbsItems=new ArrayList<MenuItem>();
        //业务层获取
        List<KnowledgeBase> kbs=knowledgeBaseService.findAllKnowledgeBase();
        kBNameKBIdMap.clear();
        for(int i=0;i<kbs.size();i++){
            String name=kbs.get(i).getName();
            MenuItem m=new MenuItem(name);
            kBNameKBIdMap.put(name,kbs.get(i).getId());

            m.setOnAction((ActionEvent e)->{
                curKBId= kBNameKBIdMap.get(m.getText());
                loadKnowledgeBase();
            });
            kbsItems.add(m);
        }
        open.getItems().setAll(kbsItems);
    }

    /**
     * 加载知识库的前期工作
     */
    public void loadKnowledgeBaseClear(){
        curNote=null;
        curNoteId=0;
        curCatalog=null;
        curNoteId=0;
        catalogList.getItems().setAll();//清空目录列表
        noteList.getItems().setAll();//清空笔记列表
        title.setText("");//清空标题
        webEngine.executeScript("clear();");;//清空编辑器
    }
    /**
     * 将当前的KnowledgeBase的目录加载到ui界面
     */
    public void loadKnowledgeBase(){
        loadKnowledgeBaseClear();
        curKB=knowledgeBaseService.findKnowledgeBaseById(curKBId);
        List<Catalog> catalogs=curKB.getCatalogList();
        List<String> catalogsName=new ArrayList<String>();
        catalogNameCatalogIdMap.clear();
        for (Catalog catalog : catalogs) {
            catalogNameCatalogIdMap.put(catalog.getName(),catalog.getId());
            catalogsName.add(catalog.getName());
        }
        catalogList.getItems().setAll(catalogsName);
    }

    /**
     * 加载目录的前期工作
     */
    public void loadCatalogClear(){
        curNoteId=0;
        curNote=null;
        title.setText("");//清空标题
        webEngine.executeScript("clear();");;//清空编辑器
    }
    /**
     * 将当前的catalog类加载到ui界面
     */
    public void loadCatalog(){
        try{
            loadCatalogClear();
            curCatalog=catalogService.findCatalogById(curCatalogId);
            List<Note> notes=curCatalog.getNotes();
            List<String> notesName=new ArrayList<String>();
            noteNameNoteIdMap.clear();
            for (Note note: notes) {
                noteNameNoteIdMap.put(note.getName(),note.getId());
                notesName.add(note.getName());
            }
            noteList.getItems().setAll(notesName);
        }catch (Exception e){
            return;
        }
    }

    /**
     * 加载笔记的前期工作
     */
    public void loadNoteClear(){
        title.setText("");//清空标题
        webEngine.executeScript("clear();");;//清空编辑器
    }
    /**
     * 将笔记加载到ui
     */
    public void loadNote(){
        loadNoteClear();
        curNote=noteService.findNoteById(curNoteId);
        title.setText(curNote.getTitle());

        String content=curNote.getContent();

        if(content!=null){
            webEngine.executeScript("setHtml('" + content + "');");
        }
    }

    /**
     * 目录点击将目录下的笔记加载到ui界面
     * @param mouseEvent
     */
    public void catalogListClicked(MouseEvent mouseEvent) {
        //1.根据点击的String来获取id
        //2.调用业务层直接根据id来获取list<note>
        //3.将list<note>转成list<string>
        //4.渲染list<string>
        String selectCatalog=catalogList.getSelectionModel().getSelectedItem();
        curCatalogId=catalogNameCatalogIdMap.get(selectCatalog);
        loadCatalog();
    }

    /**
     * 笔记列表点击将笔记内容加载到编辑器里
     * @param mouseEvent
     */
    public void noteListClicked(MouseEvent mouseEvent) {
        //1.根据点击的String来获取id
        //2.调用业务层直接根据id来获取note
        //3.将note.content放入htmlEdit
        try{

            String selectNote=noteList.getSelectionModel().getSelectedItem();
            curNoteId=noteNameNoteIdMap.get(selectNote);
            loadNote();

            //todo:将note.content放入htmlEdit

        }catch (Exception e){
            return;
        }
    }

    /**
     * 新建一个知识库
     */
    public void newKnowledgeBaseAction(ActionEvent actionEvent){
        String newKBName;
        //1.提供一个命名alert
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("");
        dialog.setHeaderText("");
        dialog.setContentText("Enter KnowLedgeBase Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            newKBName=result.get();
            if(kBNameKBIdMap.containsKey(newKBName)){
                // todo: show alert
            }
            else{
                KnowledgeBase knowledgeBase=new KnowledgeBase(newKBName);
                knowledgeBaseService.saveKnowledgeBase(knowledgeBase);
                curKBId=knowledgeBase.getId();
                curKB=knowledgeBaseService.findKnowledgeBaseById(curKBId);
                loadKnowledgeBase();
            }
        }
    }

    /**
     * 新建一个目录
     * @param actionEvent
     */
    public void newCatalogAction(ActionEvent actionEvent) {
        if(curKB==null)return;
        String newCatalogName;

        //1.提供一个命名alert
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("");
        dialog.setHeaderText("");
        dialog.setContentText("Enter Catalog Name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            newCatalogName=result.get();
            if(catalogNameCatalogIdMap.containsKey(newCatalogName)){
                System.out.println("有同名");
                // todo: show alert
            }
            else{
                Catalog catalog=new Catalog(curKBId,newCatalogName);
                catalogService.saveCatalog(catalog);
                loadKnowledgeBase();
            }
        }


        //2.把名字加到listView
        //3.名字->catalog的映射加入map

        //1.获取当前的知识库

        //2.提供命名的alert
        //3.封装一个Catalog
        //4.调用业务层的新建目录
    }

    /**
     * 新建一个笔记
     * @param actionEvent
     */
    public void newNoteAction(ActionEvent actionEvent) {
        if(curCatalog==null)return;
        String newNoteName;

        //1.提供一个命名alert
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("");
        dialog.setHeaderText("");
        dialog.setContentText("Enter Note Name:");
        Optional<String> result1 = dialog.showAndWait();
        if (result1.isPresent()) {
            newNoteName = result1.get();
            if (catalogNameCatalogIdMap.containsKey(newNoteName)) {
                // todo: show alert
            } else {
                Note note = new Note(curCatalog.getId(), newNoteName);
                noteService.saveNote(note);
                catalogList.getSelectionModel().select(catalogList.getSelectionModel().getSelectedItem());
                loadCatalog();
            }
            //1.获取当前选中的目录
            //2.提供命名的alert
            //3.封装一个note(包含cid,name)
            //4.调用业务层的新建目录
        }
    }

    /**
     * 搜索按钮实现搜索功能
     * @param actionEvent
     */
    public void searchButtonAction(ActionEvent actionEvent){
        if(curCatalog==null)return;
        try {
            String target=searchTextField.getText();
            int id=curCatalogId;
            if(id==0)return;
            curCatalog=catalogService.findCatalogById(curCatalogId);
            List<Note> notes=curCatalog.getNotes();
            List<String> notesName=new ArrayList<String>();

            for (Note note: notes) {
                notesName.add(note.getName());
            }
            List<String> matchNote = new ArrayList<String>();
            for(String s:notesName){
                if(s.indexOf(target, 0)!=-1){
                    matchNote.add(s);
                }
            }
            noteList.getItems().setAll(matchNote);
        } catch (Exception e) {
            // todo: show alert
            e.printStackTrace();
        }
    }

    public void fileImport(ActionEvent actionEvent) {
    }

    public void fileExport(ActionEvent actionEvent) {
    }

    /**
     * 保存编辑器的内容,更新笔记
     * @param actionEvent
     */
    public void editorSaveAction(ActionEvent actionEvent){
        if(curNote==null)return;
        String newContent = (String) webEngine.executeScript("getHtml();");
        String newTitle = title.getText();
        if(newContent!=curNote.getContent()||newTitle!=curNote.getTitle()){
            //当有一个出现不同，就更新
            curNote.setTitle(newTitle);
            curNote.setContent(newContent);
            noteService.updateNote(curNote);
        }


    }

    /**
     * 清空编辑器的内容
     */
    public void editorClearAction(ActionEvent actionEvent){
        webEngine.executeScript("clear();");
    }

    /**
     * 用于上下文菜单添加一个菜单项
     * @param menu
     * @param name
     * @param value
     */
    private void addMenuItem(ContextMenu menu, String name, EventHandler<ActionEvent> value) {
        MenuItem item = new MenuItem();
        item.setText(name);
        item.setOnAction(value);
        menu.getItems().add(item);
    }
    /**
     * 目录的上下文菜单
     */
    public void initCatalogListMenu() {
        ContextMenu contextMenu = new ContextMenu();

        addMenuItem(contextMenu, "new", this::newCatalogAction);
        addMenuItem(contextMenu, "rename", event -> {
            Catalog catalog = curCatalog;
            String newCatalogName;
            if (catalog != null) {
                TextInputDialog dialog = new TextInputDialog(catalog.getName());
                dialog.setTitle("");
                dialog.setHeaderText("");
                dialog.setContentText("Enter Catalog Name:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    newCatalogName = result.get();
                    if (catalogNameCatalogIdMap.containsKey(newCatalogName)) {
                        // todo: show alert
                    } else {
                        catalog.setName(newCatalogName);
                        catalogService.updateCatalog(catalog);
                        loadKnowledgeBase();
                    }
                }
            }
        });
        addMenuItem(contextMenu, "delete", event -> {
            Catalog catalog = curCatalog;
            if (catalog != null) {
                // todo: show alert
                catalogService.deleteCatalog(catalog);
                curCatalog=null;
                curCatalogId=0;
                loadKnowledgeBase();
            }
        });
        catalogList.setContextMenu(contextMenu);
    }

    /**
     * 笔记列表的上下文菜单
     */
    public void initNoteListMenu() {
        ContextMenu contextMenu = new ContextMenu();

        addMenuItem(contextMenu, "new", this::newNoteAction);
        addMenuItem(contextMenu, "rename", event -> {
            Note note = curNote;
            String newNoteName;
            if (note != null) {
                TextInputDialog dialog = new TextInputDialog(note.getName());
                dialog.setTitle("");
                dialog.setHeaderText("");
                dialog.setContentText("Enter Note Name:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    newNoteName = result.get();
                    if (noteNameNoteIdMap.containsKey(newNoteName)) {
                        // todo: show alert
                    } else {
                        note.setName(newNoteName);
                        noteService.updateNote(note);
                        catalogList.getSelectionModel().select(catalogList.getSelectionModel().getSelectedItem());
                        loadCatalog();
                    }
                }
            }
        });
        /*addMenuItem(contextMenu, "edit", event -> {
            final String note = getCurrentNote();
            if (note != null) {
                try {
                    // pass catalog and note
                    final String catalog = getCurrentCatalog();
                    FXMLLoader loader = new FXMLLoader(getFxml("editor.fxml"));
                    loader.setControllerFactory(controllerClass -> {
                        if (controllerClass == EditController.class) {
                            return new EditController(catalog, note);
                        } else {
                            try {
                                return controllerClass.getDeclaredConstructor().newInstance();
                            } catch (Exception exc) {
                                throw new RuntimeException(exc); // just bail
                            }
                        }
                    });

                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getCss("styles.css"));

                    Stage editStage = new Stage();

                    editStage.setTitle("The Note");
                    editStage.setScene(scene);
                    editStage.show();
                    editStage.setOnCloseRequest(evt -> {
                        try {
                            showNoteContent();
                        } catch (IOException e) {
                            // ignore
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/
        /*addMenuItem(contextMenu, "copy", event -> {
            String note = getCurrentNote();
            if (note != null) {
                clipboardNote = note;
                clipboardCatalog = getCurrentCatalog();
            }
        });*/
        /*addMenuItem(contextMenu, "paste", event -> {
            String catalog = getCurrentCatalog();
            if (clipboardNote != null && clipboardCatalog != null && catalog != null) {
                if (existsNote(catalog, clipboardNote)) {
                    // todo: show alert
                } else {
                    try {
                        copyNote(clipboardCatalog, clipboardNote, catalog);
                        noteList.getItems().add(clipboardNote);
                    } catch (IOException e) {
                        // todo: show alert
                        e.printStackTrace();
                    }
                }
            }
        });*/
        addMenuItem(contextMenu, "delete", event -> {
            Note note=curNote;
            if (curNote != null) {
                // todo: show alert
                noteService.deleteNote(note);
                curNote=null;
                curNoteId=0;
                loadCatalog();
            }
        });

        noteList.setContextMenu(contextMenu);

    }
}

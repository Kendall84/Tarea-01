package controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.util.Duration;
import model.ArrayPainter;
import model.SearchEngine;
import model.SearchResult;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnBinAnimate;
    @FXML
    private ListView listBinSteps;
    @FXML
    private Button btnBinGen;
    @FXML
    private Slider sliderBinSize;
    @FXML
    private Button btnBinSearch;
    @FXML
    private TextField txtBinValue;
    @FXML
    private Canvas canvasBin;
    @FXML
    private Label lblBinTime;
    @FXML
    private Label lblBinSize;
    @FXML
    private Label lblBinArray;
    @FXML
    private Button btnBinReset;
    @FXML
    private Label lblBinComplexity;

    private final SearchEngine searchEngine = new SearchEngine();
    private final ArrayPainter arrayPainter = new ArrayPainter();
    private Timeline animation;
    private int[] binArray;
    private SearchResult binResult;
    @FXML
    private Label lblBinResult;
    @FXML
    private ProgressBar progressBanBin;
    @FXML
    private Label lblComps;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupBinTab();
    }

    private void runSearch(boolean animate) {
        if(binArray == null){
            showError(txtBinValue, "Primero genere un arreglo");

            return;
        }

        /**
         * tomaos el valor a buscar al textField
         * si el usuario no indico ningun valor ,
         * asignamos uno de los que existen en el arreglo
         */

        int value;
        try {
            value = Integer.parseInt(txtBinValue.getText());
        } catch (NumberFormatException e) {
            //elegimos un elemento al azar del arreglo
            value = binArray[new Random().nextInt(binArray.length)];
            txtBinValue.setText(String.valueOf(value));

        };
        binArray=SearchEngine.ensureContains(binArray,value);
        updateArrayLabel(lblBinArray,binArray);
        SearchResult searchResult = searchEngine.binary(binArray,value);

        //llenamos el listview
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i< searchResult.steps.size(); i++){
            SearchResult.Step step = searchResult.steps.get(i);
            items.add(String.format("[%02d %s", i+1, step.description));
        }
        listBinSteps.setItems(items);

        //actualizar estadistica
        updateStats(lblBinResult,lblComps,lblBinTime,lblBinComplexity, searchResult);


        //
        if (animate){

            animateSearch(searchResult, binArray, canvasBin, progressBanBin, listBinSteps);

        }else {
            //pintamos en el canvas
            boolean[] vis = buildVisited(searchResult.steps, binArray.length,
                    searchResult.steps.size());

            SearchResult.Step last = searchResult.steps.isEmpty() ?null:
                    searchResult.steps.getLast();

            arrayPainter.paint(canvasBin, binArray, last, vis, searchResult.foundIndex);
            progressBanBin.setProgress(1.0);
        }

    }

    private void animateSearch(SearchResult res, int[] arr, Canvas canvas,
                               ProgressBar pb, ListView<String> lv) {
        stopAnimation();
        if (res.steps.isEmpty()) return;

        int total = res.steps.size();
        int delay = Math.max(180, Math.min(800, 3000 / total));

        animation = new Timeline();
        for (int i = 1; i <= total; i++) {
            final int step = i;
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(step * delay), e -> {
                SearchResult.Step s  = res.steps.get(step - 1);
                boolean[] vis = buildVisited(res.steps, arr.length, step - 1);
                int found = s.isHit ? s.index : (step == total ? res.foundIndex : -1);
                arrayPainter.paint(canvas, arr, s, vis, found);
                pb.setProgress((double) step / total);
                lv.scrollTo(step - 1);
                lv.getSelectionModel().select(step - 1);
            }));
        }
        animation.setOnFinished(e -> pb.setProgress(1.0));
        animation.play();
    }

    private void stopAnimation() {
        if (animation != null) { animation.stop(); animation = null; }
    }

    private void updateStats(Label lblBinResult, Label lblBinComps, Label lblBinTime, Label lblBinComplex, SearchResult searchResult) {

        if (searchResult == null) {
            clearStats(lblBinResult, lblBinComps, lblBinTime, lblBinComplex);
            return;
        }

        if (searchResult.isFound()) {
            lblBinResult.setText("Encontrado en índice: " + searchResult.foundIndex);
            lblBinResult.setStyle("-fx-text-fill: #2ECC71; -fx-font-weight: bold;");
        } else {
            lblBinResult.setText("No encontrado");
            lblBinResult.setStyle("-fx-text-fill: #E74C3C; -fx-font-weight: bold;");
        }


        lblBinComps.setText(String.valueOf(searchResult.comparisons));
        lblBinComps.setStyle("-fx-text-fill: #3498DB;");


        double timeMs = searchResult.nanoTime / 1_000_000.0;
        lblBinTime.setText(String.format("%.4f ms", timeMs));
        lblBinTime.setStyle("-fx-text-fill: #9B59B6;");


        lblBinComplex.setText(searchResult.complexityLabel());
        lblBinComplex.setStyle("-fx-text-fill: #F39C12;");
    }

    private void showError(TextField txt, String msg) {
        txt.setStyle("-fx-border-color: #E74C3C;");
        txt.setPromptText(msg);
        txt.setText("");

    }

    private void setupBinTab() {
        configSlider(sliderBinSize, 10, 50, 20, lblBinSize);
        btnBinGen.setOnAction(event -> generateBin());
        btnBinSearch.setOnAction(actionEvent -> runSearch(false));
        btnBinAnimate.setOnAction(actionEvent -> runSearch(true));

    }

    private void generateBin(){
        int size = (int) sliderBinSize.getValue();
        binArray= SearchEngine.generateSorted(size,size*15);
        binResult= null;
        repaintBin();
        updateArrayLabel(lblBinArray, binArray);
    }

    private void repaintBin(){

        if (binResult == null) return;
        SearchResult.Step step = null;
        boolean [] visited = null;
        int found = -1;
        if (binResult!=null){
            step = binResult.steps.isEmpty()? null
                    : binResult.steps.getLast();
            visited= buildVisited(binResult.steps, binArray.length, binResult.steps.size());
            found = binResult.foundIndex;
        }

    }

    private void clearStats(Label... labels){
        for (Label l: labels){
            l.setText("-");
            l.setStyle("");
        }
    }

    private boolean[] buildVisited(List<SearchResult.Step> steps, int n, int upTo) {
        boolean[] vis = new boolean[n];
        int limit = Math.min(upTo, steps.size());
        for (int i = 0; i < limit; i++) {
            int idx = steps.get(i).index;
            if (idx >= 0 && idx < n) vis[idx] = true;
        }
        return vis;
    }

    private void updateArrayLabel(Label lbl, int[] arr) {
        if (arr == null || arr.length == 0) { lbl.setText(""); return; }
        StringBuilder sb = new StringBuilder("[");
        int show = Math.min(arr.length, 20);
        for (int i = 0; i < show; i++) {
            sb.append(arr[i]);
            if (i < show - 1) sb.append(", ");
        }
        if (arr.length > 20) sb.append(", …");
        sb.append("]  (n=").append(arr.length).append(")");
        lbl.setText(sb.toString());
    }

    private void configSlider(Slider s, int min, int max, int val, Label lbl) {
        s.setMin(min); s.setMax(max); s.setValue(val);
        s.setMajorTickUnit(5); s.setSnapToTicks(false);
        s.valueProperty().addListener((o, ov, nv) -> lbl.setText(String.valueOf(nv.intValue())));
        lbl.setText(String.valueOf(val));
    }

    private long factorial(int n, ObservableList<String> steps, long[] calls) {
        calls[0]++;
        steps.add("factorial(" + n + ")");

        if (n <= 1) {
            steps.add("return 1");
            return 1;
        }

        long partial = factorial(n - 1, steps, calls);
        long result = n * partial;
        steps.add(n + " * factorial(" + (n - 1) + ") = " + result);
        return result;
    }
}
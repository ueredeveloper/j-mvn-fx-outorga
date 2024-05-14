package principal;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;


/* Classe para adicionar prefWidth, prefHeight, LayoutX e LayoutY nos componentes de cada tela ou tab 
 * @arrayList listNodes , array de componentes 'nodes' como botoes, caixas de texto etc
 * @array prefSizeWHeLayXY com posiçoes pref Width e Height e layout x y
 * @Pane p1, pane principal que recebe os componentes
 * */

public class Componentes
{
  public void popularTela(ArrayList<Node> listNodes, Double[][] prefSizeWHeLayXY, Pane p1)
  {
    for (int i1 = 0; i1 < prefSizeWHeLayXY.length; i1++)
    {
      Double[][] dblWHXY = new Double[prefSizeWHeLayXY.length][4];
      for (int i2 = 0; i2 < prefSizeWHeLayXY[i1].length; i2++) {
        dblWHXY[i1][i2] = prefSizeWHeLayXY[i1][i2];
      }
      ((Region)listNodes.get(i1)).setPrefSize(dblWHXY[i1][0].doubleValue(), dblWHXY[i1][1].doubleValue());
      ((Region)listNodes.get(i1)).setLayoutX(dblWHXY[i1][2].doubleValue());
      ((Region)listNodes.get(i1)).setLayoutY(dblWHXY[i1][3].doubleValue());
    }
    for (int i = 1; i < listNodes.size(); i++) {
      ((Pane)listNodes.get(0)).getChildren().add(listNodes.get(i));
    }
    ((Pane)listNodes.get(0)).getStyleClass().add("panes");
    
    p1.getChildren().addAll(new Node[] {
      (Pane)listNodes.get(0) });
  }
}


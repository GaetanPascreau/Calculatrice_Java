import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
  
public class Calculatrice extends JFrame {
  //on crée le conteneur qui va réunir tous les éléments
  private JPanel container = new JPanel();
  //Tableau stockant les éléments à afficher dans la calculatrice
  private String[] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/"};
  //Tableau réunissant les boutons: Un bouton par élément à afficher
  private JButton[] tab_button = new JButton[tab_string.length];
  //on crée le label = l'affichage des calculs
  private JLabel screen = new JLabel();
  //on définit les dimensions de nos boutons pour les chiffres
  private Dimension dim = new Dimension(50, 40);
  //on définit les dimensions de nos boutons pour les opérateurs
  private Dimension dim2 = new Dimension(50, 31);
  //on crée une variable pour stocker et afficher les chiffres
  private double number1;
  //on crée 2 booléens, pour savoir si on a déjà sélectionné un opérateur, et si on doit effacer l'écran
  private boolean clickOperator = false, update = false;
  //on crée une variable pour stocker l'opérateur
  private String operator = "";
 
  //le constructeur
  public Calculatrice(){
    this.setSize(260, 280);
    this.setTitle("Calculette");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    //On initialise le conteneur avec tous les composants
	initComposant();
	//On ajoute le conteneur
    this.setContentPane(container);
    this.setVisible(true);
  }
      
  private void initComposant(){
    //On définit la police d'écriture à utiliser dans l'affichage des calculs
    Font police = new Font("Arial", Font.BOLD, 20);
    //on écrit un Zéro par défaut sur l'écran des calculs
    screen = new JLabel("0");
    screen.setFont(police);
    //On aligne les informations à droite dans le JLabel
    screen.setHorizontalAlignment(JLabel.RIGHT);
    screen.setPreferredSize(new Dimension(200, 20));
    //on crée les 3 différents panneaux,
    //pour les chiffres, les opérateurs et l'écran d'affichage des calculs
    JPanel operator = new JPanel();      
    operator.setPreferredSize(new Dimension(55, 225));
    JPanel number = new JPanel();
    number.setPreferredSize(new Dimension(165, 225));
    JPanel panScreen = new JPanel();
    panScreen.setPreferredSize(new Dimension(220, 30));
    panScreen.setBorder(BorderFactory.createLineBorder(Color.black));
    //on ajoute le label (screen) dans son panneau (panScreen)
    panScreen.add(screen);
 
    //On parcourt le tableau initialisé avec les valeurs des boutons
    //afin de créer nos boutons
    for(int i = 0; i < tab_string.length; i++){
      tab_button[i] = new JButton(tab_string[i]);
      tab_button[i].setPreferredSize(dim);
      switch(i){
        //Pour chaque élément situé à la fin du tableau
        //et qui n'est pas un chiffre
        //on définit le comportement à avoir grâce à un listener
        //et on redimensionne le bouton si nécessaire
      //et on l'ajoute à son panneau respectif
        case 11 :
          tab_button[i].addActionListener(new EqualListener());
          number.add(tab_button[i]);
          break;
        case 12 :
          tab_button[i].setForeground(Color.red); //on écrit en rouge la valeur du bouton
          tab_button[i].addActionListener(new ResetListener());
          operator.add(tab_button[i]);
          break;
        case 13 :
          tab_button[i].addActionListener(new PlusListener());
          tab_button[i].setPreferredSize(dim2); //on définit une taille plus petite pour les boutons opérateurs
          operator.add(tab_button[i]);
          break;
        case 14 :
          tab_button[i].addActionListener(new MinusListener());
          tab_button[i].setPreferredSize(dim2);
          operator.add(tab_button[i]);
          break;	
        case 15 :	
          tab_button[i].addActionListener(new MultiListener());
          tab_button[i].setPreferredSize(dim2);
          operator.add(tab_button[i]);
          break;
        case 16 :
          tab_button[i].addActionListener(new DivListener());
          tab_button[i].setPreferredSize(dim2);
          operator.add(tab_button[i]);
          break;
        default :
          //Par défaut, ce sont les premiers éléments du tableau
          //donc des chiffres, on affecte alors le bon listener
          tab_button[i].addActionListener(new NumberListener());
          number.add(tab_button[i]);
          break;
      }
    }
    //on ajoute et positionne les 3 panneaux dans le container principal
    container.add(panScreen, BorderLayout.NORTH);
    container.add(number, BorderLayout.CENTER);
    container.add(operator, BorderLayout.EAST);
  }

  //l'organisation graphique est terminée, place aux méthodes de calcul:
  //Méthode permettant d'effectuer un calcul selon l'opérateur sélectionné
  //ATTENTION: ce n'est pas le dernier opérateur cliqué (opération non faisable car il manque le nombre après l'opérateur), mais celui d'avant qui est concerné !
  private void calcul(){
    if(operator.equals("+")){   //si on a cliqué sur le bouton "+" au tour précédent (car le bouton opérateur qu'on vient de cliquer ne sera enregistré que lorsque la méthode calcul() aura été parcourue), alors operator ="+"
    	number1 = number1 +     // on fait la somme du nombre cliqué avant ce bouton "+" (= number1) et celui encore affiché à l'écran (entré juste après le clic de ce bouton)
            Double.valueOf(screen.getText()).doubleValue();
      screen.setText(String.valueOf(number1));   //on affiche le résultat à l'écran
    }
    if(operator.equals("-")){
    	number1 = number1 - 
            Double.valueOf(screen.getText()).doubleValue();
      screen.setText(String.valueOf(number1));
    }          
    if(operator.equals("*")){
    	number1 = number1 * 
            Double.valueOf(screen.getText()).doubleValue();
      screen.setText(String.valueOf(number1));
    }     
    if(operator.equals("/")){
      try{
    	  number1 = number1 / 
              Double.valueOf(screen.getText()).doubleValue();
        screen.setText(String.valueOf(number1));
        //si on divise par zéro, on déclenche une exception qui affiche "infinity" puis "NaN" si on insiste
      } catch(ArithmeticException e) {
    	  screen.setText("0");
      }
    }
  }

  //Listener utilisé pour stocker les chiffres et les afficher
  class NumberListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
      //On va afficher le chiffre additionnel dans le label
      //pour utiliser la méthode getSource(), il faut caster e (de type ActionEvent) en JButton
      //on récupère d'abord le texte du bouton cliqué (= le nombre correspondant )dans un String
      String str = ((JButton)e.getSource()).getText();
      if(update){         //ça veut dire qu'on a utilisé un opérateur juste avant et qu'on va effacer le nombre précédent et afficher le nouveau
        update = false;   //on réinitialise update à false pour ne pas effacer les chiffres au fur et à mesure de leur affichage (afin de composer un nombre à plusieurs chiffres)
      }
      else{
        if(!screen.getText().equals("0")) //si on n'a pas utilisé un opérateur juste avant et que l'affichage n'est pas zéro
          str = screen.getText() + str;   //on ajoute le nouveau chiffre saisi devant le chiffre précédent pour composer un nombre
      }
      screen.setText(str);
    }
  }

  //Listener affecté au bouton =
  class EqualListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      calcul();
      update = true;  //signifie qu'on a utilisé un opérateur et qu'il faudra effacer l'écran au prochain clic pour afficher la saisie du nombre suivant
      clickOperator = false;  // signifie qu'il n'y a pas de calcul en cours, on en redémarre un nouveau au prochain clic
    }
  }

  //Listener affecté au bouton +
  class PlusListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clickOperator){       //si le dernier opérateur cliqué n'est pas "=", alors clickOperator est "true"
        calcul();              //on va lancer le calcul de l'opération précédente -- ex: 2+3+ affichera 5 qui est le résultat intermédiaire (2+3)
        screen.setText(String.valueOf(number1));     //on affiche ce résultat intermédiaire
      }
      else{                                                  //si on vient de taper "=" ou si on démarre, clickOperator est "false", on démarre un nouveau calcul
    	  number1 = Double.valueOf(screen.getText()).doubleValue();  //on affiche juste la valeur du bouton cliqué avant l'opérateur -- ex: 5+ affichera juste 5
    	  clickOperator = true;                             //signifie qu'un calcul est en cours et qu'on va afficher un résultat intermédiaire la prochaine fois
      }
      operator = "+";
      update = true; //au prochain clic on efface l'écran et affiche la nouvelle saisie
    }
  }

  //Listener affecté au bouton -
  class MinusListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clickOperator){
        calcul();
        screen.setText(String.valueOf(number1));
      }
      else{
    	  number1 = Double.valueOf(screen.getText()).doubleValue();
    	  clickOperator = true;
      }
      operator = "-";
      update = true;
    }
  }

  //Listener affecté au bouton *
  class MultiListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clickOperator){
        calcul();
        screen.setText(String.valueOf(number1));
      }
      else{
    	  number1 = Double.valueOf(screen.getText()).doubleValue();
    	  clickOperator = true;
      }
      operator = "*";
      update = true;
    }
  }

  //Listener affecté au bouton /
  class DivListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clickOperator){
        calcul();
        screen.setText(String.valueOf(number1));
      }
      else{
    	  number1 = Double.valueOf(screen.getText()).doubleValue();
    	  clickOperator = true;
      }
      operator = "/";
      update = true;
    }
  }

  //Listener affecté au bouton de remise à zéro
  class ResetListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
    	clickOperator = false; //signifie qu'il n'y a pas de calcul en cours
      update = true;           //on efface l'écran
      number1 = 0;			   //on réinitialise le résultat à 0
      operator = "";
      screen.setText("0");    //on affiche "0" à l'écran
    }
  }      
}
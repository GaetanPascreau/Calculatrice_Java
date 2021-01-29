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
  //on cr�e le conteneur qui va r�unir tous les �l�ments
  private JPanel container = new JPanel();
  //Tableau stockant les �l�ments � afficher dans la calculatrice
  private String[] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/"};
  //Tableau r�unissant les boutons: Un bouton par �l�ment � afficher
  private JButton[] tab_button = new JButton[tab_string.length];
  //on cr�e le label = l'affichage des calculs
  private JLabel screen = new JLabel();
  //on d�finit les dimensions de nos boutons pour les chiffres
  private Dimension dim = new Dimension(50, 40);
  //on d�finit les dimensions de nos boutons pour les op�rateurs
  private Dimension dim2 = new Dimension(50, 31);
  //on cr�e une variable pour stocker et afficher les chiffres
  private double number1;
  //on cr�e 2 bool�ens, pour savoir si on a d�j� s�lectionn� un op�rateur, et si on doit effacer l'�cran
  private boolean clickOperator = false, update = false;
  //on cr�e une variable pour stocker l'op�rateur
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
    //On d�finit la police d'�criture � utiliser dans l'affichage des calculs
    Font police = new Font("Arial", Font.BOLD, 20);
    //on �crit un Z�ro par d�faut sur l'�cran des calculs
    screen = new JLabel("0");
    screen.setFont(police);
    //On aligne les informations � droite dans le JLabel
    screen.setHorizontalAlignment(JLabel.RIGHT);
    screen.setPreferredSize(new Dimension(200, 20));
    //on cr�e les 3 diff�rents panneaux,
    //pour les chiffres, les op�rateurs et l'�cran d'affichage des calculs
    JPanel operator = new JPanel();      
    operator.setPreferredSize(new Dimension(55, 225));
    JPanel number = new JPanel();
    number.setPreferredSize(new Dimension(165, 225));
    JPanel panScreen = new JPanel();
    panScreen.setPreferredSize(new Dimension(220, 30));
    panScreen.setBorder(BorderFactory.createLineBorder(Color.black));
    //on ajoute le label (screen) dans son panneau (panScreen)
    panScreen.add(screen);
 
    //On parcourt le tableau initialis� avec les valeurs des boutons
    //afin de cr�er nos boutons
    for(int i = 0; i < tab_string.length; i++){
      tab_button[i] = new JButton(tab_string[i]);
      tab_button[i].setPreferredSize(dim);
      switch(i){
        //Pour chaque �l�ment situ� � la fin du tableau
        //et qui n'est pas un chiffre
        //on d�finit le comportement � avoir gr�ce � un listener
        //et on redimensionne le bouton si n�cessaire
      //et on l'ajoute � son panneau respectif
        case 11 :
          tab_button[i].addActionListener(new EqualListener());
          number.add(tab_button[i]);
          break;
        case 12 :
          tab_button[i].setForeground(Color.red); //on �crit en rouge la valeur du bouton
          tab_button[i].addActionListener(new ResetListener());
          operator.add(tab_button[i]);
          break;
        case 13 :
          tab_button[i].addActionListener(new PlusListener());
          tab_button[i].setPreferredSize(dim2); //on d�finit une taille plus petite pour les boutons op�rateurs
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
          //Par d�faut, ce sont les premiers �l�ments du tableau
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

  //l'organisation graphique est termin�e, place aux m�thodes de calcul:
  //M�thode permettant d'effectuer un calcul selon l'op�rateur s�lectionn�
  //ATTENTION: ce n'est pas le dernier op�rateur cliqu� (op�ration non faisable car il manque le nombre apr�s l'op�rateur), mais celui d'avant qui est concern� !
  private void calcul(){
    if(operator.equals("+")){   //si on a cliqu� sur le bouton "+" au tour pr�c�dent (car le bouton op�rateur qu'on vient de cliquer ne sera enregistr� que lorsque la m�thode calcul() aura �t� parcourue), alors operator ="+"
    	number1 = number1 +     // on fait la somme du nombre cliqu� avant ce bouton "+" (= number1) et celui encore affich� � l'�cran (entr� juste apr�s le clic de ce bouton)
            Double.valueOf(screen.getText()).doubleValue();
      screen.setText(String.valueOf(number1));   //on affiche le r�sultat � l'�cran
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
        //si on divise par z�ro, on d�clenche une exception qui affiche "infinity" puis "NaN" si on insiste
      } catch(ArithmeticException e) {
    	  screen.setText("0");
      }
    }
  }

  //Listener utilis� pour stocker les chiffres et les afficher
  class NumberListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
      //On va afficher le chiffre additionnel dans le label
      //pour utiliser la m�thode getSource(), il faut caster e (de type ActionEvent) en JButton
      //on r�cup�re d'abord le texte du bouton cliqu� (= le nombre correspondant )dans un String
      String str = ((JButton)e.getSource()).getText();
      if(update){         //�a veut dire qu'on a utilis� un op�rateur juste avant et qu'on va effacer le nombre pr�c�dent et afficher le nouveau
        update = false;   //on r�initialise update � false pour ne pas effacer les chiffres au fur et � mesure de leur affichage (afin de composer un nombre � plusieurs chiffres)
      }
      else{
        if(!screen.getText().equals("0")) //si on n'a pas utilis� un op�rateur juste avant et que l'affichage n'est pas z�ro
          str = screen.getText() + str;   //on ajoute le nouveau chiffre saisi devant le chiffre pr�c�dent pour composer un nombre
      }
      screen.setText(str);
    }
  }

  //Listener affect� au bouton =
  class EqualListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      calcul();
      update = true;  //signifie qu'on a utilis� un op�rateur et qu'il faudra effacer l'�cran au prochain clic pour afficher la saisie du nombre suivant
      clickOperator = false;  // signifie qu'il n'y a pas de calcul en cours, on en red�marre un nouveau au prochain clic
    }
  }

  //Listener affect� au bouton +
  class PlusListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clickOperator){       //si le dernier op�rateur cliqu� n'est pas "=", alors clickOperator est "true"
        calcul();              //on va lancer le calcul de l'op�ration pr�c�dente -- ex: 2+3+ affichera 5 qui est le r�sultat interm�diaire (2+3)
        screen.setText(String.valueOf(number1));     //on affiche ce r�sultat interm�diaire
      }
      else{                                                  //si on vient de taper "=" ou si on d�marre, clickOperator est "false", on d�marre un nouveau calcul
    	  number1 = Double.valueOf(screen.getText()).doubleValue();  //on affiche juste la valeur du bouton cliqu� avant l'op�rateur -- ex: 5+ affichera juste 5
    	  clickOperator = true;                             //signifie qu'un calcul est en cours et qu'on va afficher un r�sultat interm�diaire la prochaine fois
      }
      operator = "+";
      update = true; //au prochain clic on efface l'�cran et affiche la nouvelle saisie
    }
  }

  //Listener affect� au bouton -
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

  //Listener affect� au bouton *
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

  //Listener affect� au bouton /
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

  //Listener affect� au bouton de remise � z�ro
  class ResetListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
    	clickOperator = false; //signifie qu'il n'y a pas de calcul en cours
      update = true;           //on efface l'�cran
      number1 = 0;			   //on r�initialise le r�sultat � 0
      operator = "";
      screen.setText("0");    //on affiche "0" � l'�cran
    }
  }      
}
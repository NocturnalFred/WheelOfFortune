package wheelOfFortune;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Fred Rodd && Micheal Ritchhart
 *
 */
public class WheelOfFortune {

	/**
	 * @param args
	 */
	public static Wheel Wheel;
	public static Phrase Phrase;
	public static Letters Letters;
	public static JLabel Directions;
	public static GameLogicController Controller;
	public static Player[] players;
        public static JFrame topLevelFrame;
        private static JPanel center;
        private static JPanel topLevelPanel;
        private static JPanel CenterDivisor;
        private static JPanel introScreen;
        private static JLabel welcomeLabel;
        private static JLabel selectLabel;
        private static JButton onePlayer;
        private static JButton twoPlayer;
        private static JButton threePlayer;
        private static JButton Bobject;
        private static int amtOfPlayers;
        private static phraseGenerator mainPhrases;
        private static phraseGenerator bobPhrases;
        static JPanel PlayerInfo;

        
	public static void main(String[] args) throws IOException {
		
		topLevelFrame = new JFrame();
		topLevelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		topLevelFrame.setVisible(true);
		mainPhrases = new phraseGenerator("WheelText.txt");
		bobPhrases = new phraseGenerator("BobText.txt");
		openingSequnce();
		
        
                topLevelFrame.pack();
		
                
	}
	
        public static void createGame(int numberOfPlayers, phraseGenerator phrase, int rounds)
        {
                players = new Player[numberOfPlayers];
                for (int i = 0; i<numberOfPlayers; ++i)
                {
                        players[i] = new Player("Player " + new Integer(i + 1).toString());
                }

                addNewPhrase(phrase, rounds);

        }

        public static void addNewPhrase(phraseGenerator phrase, int rounds){

                topLevelFrame.getContentPane().removeAll();
                topLevelFrame.repaint();
                topLevelFrame.setBackground(Color.GREEN);

                topLevelPanel = new JPanel(new BorderLayout()); //goes into the top level frame
                PlayerInfo = new JPanel();	//goes into the south of the top level panel

                for (Player play: players)
                {
                        PlayerInfo.add(play);
                }

                CenterDivisor = new JPanel(); //goes into the center of top level panel
                CenterDivisor.setLayout(new BoxLayout(CenterDivisor, BoxLayout.PAGE_AXIS));
                JPanel WheelAndLetterOptionsContainer = new JPanel(new FlowLayout());
                JButton tempSpinner = new JButton("Spin");
                JButton solvePuzz = new JButton("Solve Puzzel");
                JPanel PlayerInfoDivisor = new JPanel();
                PlayerInfoDivisor.setLayout(new BoxLayout(PlayerInfoDivisor, BoxLayout.X_AXIS));

                Controller = new GameLogicController();
                Wheel = new Wheel(Controller, tempSpinner);
                Phrase = new Phrase(phrase, Controller, solvePuzz);
                Letters = new Letters(Controller);
                Controller.linkComponents(Wheel, Phrase, Letters, players, players[0], phrase, rounds);

                JPanel VowelsAndConsonants = Letters;

                //allJpanelsCreated, now to add them to the appropriate JPanels and finally attach to 
                //top level Jframe

                WheelAndLetterOptionsContainer.add(Wheel);
                WheelAndLetterOptionsContainer.add(tempSpinner);
                WheelAndLetterOptionsContainer.add(solvePuzz);
                WheelAndLetterOptionsContainer.add(VowelsAndConsonants);

                CenterDivisor.add(Phrase);
                CenterDivisor.add(WheelAndLetterOptionsContainer);
                topLevelPanel.add(CenterDivisor);				
                PlayerInfoDivisor.add(PlayerInfo);
                PlayerInfoDivisor.add(Controller);
                topLevelPanel.add(PlayerInfoDivisor , BorderLayout.SOUTH);
                topLevelFrame.add(topLevelPanel);
                topLevelFrame.pack();

                topLevelFrame.setVisible(true);
        }

        public static void openingSequnce()
        {
            introScreen = new JPanel();
            welcomeLabel = new JLabel("Welcome to the Wheel of Fortune!");
            selectLabel = new JLabel("        Please Select The Desired Player Amount");
            onePlayer = new JButton("1 Player");
            twoPlayer = new JButton("2 Player");
            threePlayer = new JButton("3 Player");
            Bobject = new JButton("Bob Phrases");
            center = new JPanel();
            introScreen = new JPanel();


            topLevelFrame.getContentPane().removeAll();
            topLevelFrame.repaint();
            topLevelFrame.setBackground(Color.GREEN);



            // Interface Panel with Box Layout	
            introScreen.setLayout(new BoxLayout(introScreen, BoxLayout.PAGE_AXIS));

            introScreen.setBackground(Color.BLUE);
            introScreen.setMinimumSize(new Dimension(1200, 200));
            introScreen.setPreferredSize(new Dimension(1200, 200));

            welcomeLabel.setPreferredSize(new Dimension(900, 100));
            welcomeLabel.setFont(new Font("Serif", Font.BOLD, 34));

            selectLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
    //	        introScreen.add(Box.createVerticalGlue());

            introScreen.add(welcomeLabel); //add Welcome Jlabel to IntroScreen JPanel
            introScreen.add(selectLabel);
            introScreen.add(Box.createRigidArea(new Dimension(0,20)));


            center.setBackground(Color.CYAN);
    //	        center.setMinimumSize(new Dimension(300, 300)); //set min size of JPanel within gridbagLayout
    //	        center.setPreferredSize( new Dimension(300, 300)); //set Preferred size of Jpanel

            introScreen.add(center); //add inner Jpanel to outter (IntroScreen) JPanel
            introScreen.add(Box.createRigidArea(new Dimension(0,20)));



            onePlayer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                amtOfPlayers = 1;
                createGame(amtOfPlayers, mainPhrases, 1);
            }
            });

            twoPlayer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                amtOfPlayers = 2;
                createGame(amtOfPlayers, mainPhrases, 1);
            }
            });

            threePlayer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                amtOfPlayers = 3;
                createGame(amtOfPlayers, mainPhrases, 2);
            }
            });

            Bobject.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        amtOfPlayers = 1;
                        createGame(amtOfPlayers, bobPhrases, 1);
                        }
            });



            center.add(onePlayer);
            center.add(twoPlayer);
            center.add(threePlayer);
            center.add(Bobject);

            topLevelFrame.add(introScreen);
            topLevelFrame.pack();

            topLevelFrame.setVisible(true);
        }


        public static void displayWinningGraphics(Player winner)
        {
                Random ranNum = new Random();
                String[] pithyTitles = {
                                "The glorious", "The celebrated", "The venerated", "The distinguished",
                                "The magnificient", "The spectacular"					
                };

                String[] pithyEndingLines = {
                                "has defeated the game of wheels", 
                                "has beaten the odds",
                                "has shamed us all",
                                "has done his family proud"

                };
                topLevelFrame.getContentPane().removeAll();
                topLevelFrame.repaint();
                JPanel winningPanel = new JPanel(new BorderLayout());
                JPanel container = new JPanel();
                JLabel winningStatement = new JLabel(pithyTitles[(ranNum.nextInt(pithyTitles.length))] + 
                                " " + winner.getName() + " " + pithyEndingLines[(ranNum.nextInt(pithyEndingLines.length))]
                                                + " with a score of " + winner.getPoints());
                winningPanel.setFont (winningPanel.getFont ().deriveFont (1.0f));
                container.add(winningStatement);
                JButton playAgain = new JButton("Play Another Game");
                playAgain.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        openingSequnce();
                }
                });
                container.add(playAgain);
                winningPanel.add(container, BorderLayout.CENTER);
                topLevelFrame.add(winningPanel);
                topLevelFrame.pack();	
                topLevelFrame.setVisible(true);
        }

        public static void displayLosingGraphics(Player loser)
        {
                Random ranNum = new Random();
                String[] pithyTitles = {
                                "The failure", "The unwanted", "The has-been", "The underprivileged",
                                "The deadbeat", "The spectacular"					
                };

                String[] pithyEndingLines = {
                                "has lost the game and yet can boast he did it", 
                                "failed to win even",
                                "will not be impressing anyone",
                };

                topLevelFrame.getContentPane().removeAll();
                topLevelFrame.repaint();
                JPanel losingPanel = new JPanel(new BorderLayout());
                JPanel container = new JPanel();
                JLabel losingStatement = new JLabel(pithyTitles[(ranNum.nextInt(pithyTitles.length))] + 
                                " " + loser.getName() + " " + pithyEndingLines[(ranNum.nextInt(pithyEndingLines.length))]
                                                + " with a score of " + loser.getPoints());
                losingStatement.setFont (losingStatement.getFont ().deriveFont (15.0f));
                container.add(losingStatement);
                JButton playAgain = new JButton("Play Another Game");
                playAgain.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        openingSequnce();
                }
                });
                container.add(playAgain);
                losingPanel.add(container, BorderLayout.CENTER);
                topLevelFrame.add(losingPanel);
                topLevelFrame.pack();	
                topLevelFrame.setVisible(true);
        }
          
}

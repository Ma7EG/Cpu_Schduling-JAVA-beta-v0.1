package cpu2;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                panel p = new panel();
                p.setVisible(true); 
            }
        });
    }
}

import javax.swing.*;

public class MainWindow extends JFrame {

    //Создание конструктора основного окна
    public MainWindow() {//Конструктор окна
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Прекращение работы программы при нажатии на крестик
        setSize( 320,345); //height = width + 25 - потому как 25 пикселей занимает верхняя часть окошка.
        setLocation(400, 300);
        add(new GameField());//Игровое поле
        setVisible(true);
    }

    public static void main(String args[]){
        //Место с которого начинается работа программы
        MainWindow mw = new MainWindow(); //Создание экземпляра класса MainWindow
    }
}

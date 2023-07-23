import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16; //Де фолтный размер пикселя(Клетки), змейки и яблочка в Пикселях
    // 320/16 = 20 на 20 клеточек игрового пространства
    private final int ALL_DOTS = 400; //Сколько всего единиц игровых может поместиться на игровом поле
    private Image dot; //Рисунок клетки
    private Image apple; //Рисунок яблока
    private int appleX; //Позиция яблока в пространстве по Х координате
    private int appleY; //Позиция яблока в пространстве по Y координате

    //Массивы для хранения всех положений змейки
    private int[] x = new int[ALL_DOTS]; //Массив размерностью ALL_DOTS, Масимальная размерность
    private int[] y = new int[ALL_DOTS]; //Тоже самое для Y
    private int dots; //Размер змейки в данный момент времени
    private Timer timer; // Таймер
    //boolean за текущее направление змейки
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true; //Указание того, что мы в игре слились или нет




    public GameField(){ //Конструктор Объекта
        setBackground(Color.white);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    //Метод который инициализирует начало игры
    public void initGame(){ //Это начальные значения для змейки
        dots = 3;
        for(int i = 0 ; i < dots ; i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }

        timer = new Timer(200,this); //создание таймера, delay(250) - с какой частотой они будут тикать
        //this = означает что, вот ЭТОТ класс "GameField" Будет отвечать за обработку каждого вызова таймера
        timer.start();
        //Метод для создания яблока
        createApple();
    }

    public void createApple(){
        //DOT_SIZE - Размерность поля
        appleX = new Random().nextInt(20)*DOT_SIZE;//20 - означает общее количество общих допустимых позиций
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }
    public void loadImages(){
        //Чтобы загрузить картинки используем ImageIcon
        //iia = ImageIconApple
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage(); //Присваивание карики переменной "apple"
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage(); //Присваивание карики переменной "dot"
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple, appleX,appleY,this);
            for(int i = 0; i < dots; i++){
                g.drawImage(dot,x[i],y[i],this);
            }
        }else {
            String str = "Game Over";
            //Font f = new Font("Arial", 14, Font.BOLD);//BOLD - жирный
            g.setColor(Color.black);//Цвет отрисовки
            //g.setFont(f);//Шрифт
            g.drawString(str,125,SIZE/2);//Отрисовка строки.
        }
    }

    public void move(){ //Логическая перерисовка положения змейки
        for(int i = dots; i > 0; i--){ //передвижение по позициям X и Y
            x[i] = x[i-1]; // Точки которые не голова, переместили на предыдущие позиции
            y[i] = y[i-1];
        }
        //А для головы,в том направлении в котором указано направление
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for(int i = dots; i > 0; i--){
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if(x[0] > SIZE){
            inGame = false;
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(y[0] > SIZE){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){//Если игрок в игре ~~
            checkApple();
            checkCollisions();
            move();//Движение змейки
        }
        repaint();//Перерисовка поля, метод вызывающий paintComponent - отрисовка всех компонентов в String
    }
    //Создание нового класса для обработки клавиш
    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && ! right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && ! left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && ! down){
                up = true;
                right = false;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && ! up){
                down = true;
                right = false;
                left = false;
            }
        }
    }
}

package gamePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class GameGraphics implements Runnable {

	private Display display;

	private int width;
	private int height;
	private String title;

	private String[][] board;

	private Thread thread;
	private BufferStrategy buffer;
	private Graphics g;

	private int BOX_width = 80;
	private int BOX_height = 80;

	private int paddingX = 80;
	private int paddingY = 80;

	private int boardRow;
	private int boardCol;

	private boolean isGameEnd = false;
	private boolean isGameStarted = false;

	private int sourceI;
	private int sourceJ;

	private Game game;

	public GameGraphics() {
		this.width = GameSettings.width;
		this.height = GameSettings.height;
		this.title = GameSettings.title;

		sourceI = GameSettings.sourceI;
		sourceJ = GameSettings.sourceJ;

		boardRow = GameSettings.BOARD_ROW;
		boardCol = GameSettings.BOARD_COL;

		display = new Display(width, height, title);
		game = new Game();

	}

	private void init() {

		// black = ImageLoader.loadImage("/Images/bl1.png", BOX_width,
		// BOX_height);
		// white = ImageLoader.loadImage("/Images/w1.png", BOX_width,
		// BOX_height);
		// mouseIndicator = ImageLoader.loadImage("/Images/w1.png", BOX_width,
		// BOX_height);
		// background = ImageLoader.loadImage("/Images/background3.jpg", width,
		// height);
		// logo = ImageLoader.loadImage("/Images/logo.png");

		// display.canvas.addKeyListener(this);

	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

	}

	private void drawBoard(Graphics g) {

		drawBackground(g);
		Color color = Color.black;
		int lineWeight = 1;
		g.setColor(color);
		for (int i = paddingX; i <= (boardRow * BOX_width) + paddingX; i += BOX_width) {
			for (int k = i - lineWeight; k < i + lineWeight; k++)
				g.drawLine(k, paddingY, k, BOX_height * boardRow + BOX_height);

		}

		for (int i = paddingY; i <= (boardCol * BOX_height) + paddingY; i += BOX_height) {
			for (int k = i - lineWeight; k < i + lineWeight; k++)
				g.drawLine(paddingX, k, BOX_width * boardCol + BOX_width, k);
		}

	}

	private void drawElement(Graphics g) {
		for (int i = 0; i < boardRow; i++) {
			for (int j = 0; j < boardCol; j++) {
				if (!board[i][j].equals("E")) {
					String stringToDraw = "";
					boolean isBGS = true;

					if (board[i][j].equals("G") || board[i][j].equals("W") || board[i][j].equals("P")) {
						isBGS = false;

					}
					stringToDraw = board[i][j];

					if (i == sourceI && j == sourceJ)
						stringToDraw = "Start";

					g.setColor(Color.black);
					int x = (j + 1) * BOX_width;
					int y = (i + 1) * BOX_height;

					Font font = null;

					if (isBGS) {
						font = new Font("Century Gothic", Font.PLAIN, 30);
					} else {
						font = new Font("Century Gothic", Font.CENTER_BASELINE, 70);
					}
					g.setFont(font);

					g.drawString(stringToDraw, x + 10, y + 60);
				}
			}
		}
	}

	private void play() {
		// --
	}

	private void render() {
		buffer = display.canvas.getBufferStrategy();
		if (buffer == null) {
			display.canvas.createBufferStrategy(3);
			return;
		}

		g = buffer.getDrawGraphics();

		g.clearRect(0, 0, width, height);
		drawBoard(g);
		drawElement(g);

		buffer.show();
		g.dispose();

	}

	@Override
	public void run() {
		init();

		board = game.initialiseBoard();

		while (true) {
			render();
			if (!isGameEnd)
				play();
		}
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();

	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

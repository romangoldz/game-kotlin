// MyGdxGame.java (у core)
public class MyGdxGame extends Game {
    @Override
    public void create() {
        setScreen(new Main());   // Main extends Screen — тепер ок
    }
}

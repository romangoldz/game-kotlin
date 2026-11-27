package com.mygams;

import com.badlogic.gdx.Game;
import com.mygams.Main;   // якщо Main в тому ж пакеті — імпорт не потрібен

public class MyGdxGame extends Game {
    @Override
    public void create() {
        setScreen(new Main());
    }
}

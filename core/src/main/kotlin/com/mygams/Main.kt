
package com.mygams

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader
import com.badlogic.gdx.math.Vector3

class Main : ApplicationAdapter() {

    private lateinit var camera: PerspectiveCamera
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment

    private lateinit var model: Model
    private lateinit var player: ModelInstance

    private var rotationX = 0f
    private var rotationY = 0f
    private var zoom = 5f

    private var lastX = 0
    private var lastY = 0

    override fun create() {

        // Камера
        camera = PerspectiveCamera(
            67f,
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        )

        camera.position.set(0f, 0f, zoom)
        camera.lookAt(0f, 0f, 0f)
        camera.near = 0.1f
        camera.far = 100f
        camera.update()

        // Рендерер
        modelBatch = ModelBatch()

        // Освітлення
        environment = Environment()

        environment.set(
            ColorAttribute.createAmbientLight(
                0.7f, 0.7f, 0.7f, 1f
            )
        )

        environment.add(
            DirectionalLight().set(
                0.8f, 0.8f, 0.8f,
                -1f, -0.8f, -0.2f
            )
        )

        // Завантаження OBJ
        val loader = ObjLoader()

        model = loader.loadModel(
            Gdx.files.internal("models/player.obj")
        )

        player = ModelInstance(model)

        // Керування мишкою
        Gdx.input.inputProcessor = object : InputAdapter() {

            override fun touchDown(
                screenX: Int,
                screenY: Int,
                pointer: Int,
                button: Int
            ): Boolean {
                lastX = screenX
                lastY = screenY
                return true
            }

            override fun touchDragged(
                screenX: Int,
                screenY: Int,
                pointer: Int
            ): Boolean {

                val dx = screenX - lastX
                val dy = screenY - lastY

                rotationY += dx * 0.5f
                rotationX += dy * 0.5f

                lastX = screenX
                lastY = screenY

                return true
            }

            override fun scrolled(
                amountX: Float,
                amountY: Float
            ): Boolean {

                zoom += amountY * 0.5f
                zoom = zoom.coerceIn(2f, 20f)

                return true
            }
        }
    }

    override fun render() {

        // Очищення екрана
        Gdx.gl.glViewport(
            0, 0,
            Gdx.graphics.width,
            Gdx.graphics.height
        )

        Gdx.gl.glClearColor(
            0.12f, 0.12f, 0.15f, 1f
        )

        Gdx.gl.glClear(
            GL20.GL_COLOR_BUFFER_BIT or
            GL20.GL_DEPTH_BUFFER_BIT
        )

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST)

        // Обертання моделі
        player.transform.idt()

        player.transform.rotate(
            Vector3.X,
            rotationX
        )

        player.transform.rotate(
            Vector3.Y,
            rotationY
        )

        // Оновлення камери
        camera.position.set(0f, 0f, zoom)
        camera.lookAt(0f, 0f, 0f)
        camera.update()

        // Рендеринг
        modelBatch.begin(camera)
        modelBatch.render(player, environment)
        modelBatch.end()

        // Вихід клавішею ESC
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }

    override fun dispose() {
        modelBatch.dispose()
        model.dispose()
    }
}

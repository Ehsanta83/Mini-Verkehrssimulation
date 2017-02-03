
package de.tu_clausthal.in.meclab.verkehrssimulation.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.List;

/**
 * screen entry point, all graphical components based on the LibGDX library
 */
public class CScreen extends ApplicationAdapter
{
    /**
     * environment tilemap reference
     */
    private final ITileMap m_environment;
    /**
     * sprite list
     */
    private final List<? extends ISprite> m_sprites;
    /**
     * camera definition
     */
    private OrthographicCamera m_camera;
    /**
     * sprite batch painting
     */
    private SpriteBatch m_spritebatch;
    /**
     * renderer
     */
    private OrthogonalTiledMapRenderer m_render;
    /**
     * flag for disposed screen
     */
    private volatile boolean m_isdisposed;

    /**
     * constructor
     *
     * @param p_sprites list of sprites
     */
    public CScreen( final List<? extends ISprite> p_sprites, final ITileMap p_environment )
    {
        m_sprites = p_sprites;
        m_environment = p_environment;
    }

    @Override
    public void create()
    {
        // create orthogonal camera perspective
        final float l_unit = 1.0f / m_environment.cellsize();

        // create execution structure for painting
        m_spritebatch = new SpriteBatch();

        // create environment view and put all objects in it
        m_render = new OrthogonalTiledMapRenderer( m_environment.map(), l_unit, m_spritebatch );

        m_camera = new OrthographicCamera( m_environment.column(), m_environment.row() );
        m_camera.setToOrtho( false, m_environment.column() * l_unit, m_environment.row() * l_unit );
        m_camera.position.set( m_environment.column() / 2f, m_environment.row() / 2f, 0 );
        m_camera.zoom = m_environment.cellsize();

        // create sprites and particle systems
        m_sprites.forEach( i -> i.spriteinitialize( m_environment.cellsize(), l_unit ) );
        m_render.setView( m_camera );

    }

    @Override
    public void render()
    {
        // camera update must be the first for reaction on input device events
        m_camera.update();

        // create black background
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // environment tilemap painting
        m_render.setView( m_camera );
        m_render.render();


        // object sprite painting
        m_spritebatch.setProjectionMatrix( m_camera.combined );
        m_spritebatch.begin();

        m_sprites.forEach( i -> i.sprite().draw( m_spritebatch ) );

        renderFPS();
        m_spritebatch.end();
    }

    @Override
    public void dispose()
    {
        // dispose flag is set to stop parallel simulation execution outside the screen
        m_isdisposed = true;
        m_spritebatch.dispose();
        m_render.dispose();
        super.dispose();
    }

    /**
     * returns if the screen is disposed
     *
     * @return disposed flag
     */
    public final boolean isDisposed()
    {
        return m_isdisposed;
    }

    /**
     * render frame per second
     */
    private void renderFPS()
    {
        final int l_fps = Gdx.graphics.getFramesPerSecond();
        final BitmapFont l_fpsFont = new BitmapFont();
        if ( l_fps >= 45 )
        {
            //green
            l_fpsFont.setColor( 0, 1, 0, 1 );
        }
        else if ( l_fps >= 30 )
        {
            //yellow
            l_fpsFont.setColor( 1, 1, 0, 1 );
        }
        else
        {
            //red
            l_fpsFont.setColor( 1, 0, 0, 1 );
        }
        l_fpsFont.draw( m_spritebatch, "FPS: " + l_fps, 19, 1005 );
        //white
        l_fpsFont.setColor( 1, 1, 1, 1 );
    }
}

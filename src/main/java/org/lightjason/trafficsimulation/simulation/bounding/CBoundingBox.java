/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason TrafficSimulation                              #
 * # Copyright (c) 2016-17, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.trafficsimulation.simulation.bounding;


import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.resources.Messages;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * bounding box class
 */
public class CBoundingBox implements IBoundingBox
{
    /**
     *  The {@link BodyFixture} list
     */
    protected List<BodyFixture> m_fixtures;

    @Override
    public boolean intersects( final IBoundingBox p_boundingbox )
    {
        return false;
    }

    @Override
    public void resize( final int p_percent )
    {

    }

    @Override
    public UUID getId()
    {
        return null;
    }

    @Override
    public AABB createAABB()
    {
        return null;
    }

    @Override
    public AABB createAABB( final Transform p_transform )
    {
        return null;
    }

    @Override
    public CBoundingBox addFixture( final BodyFixture p_bodyfixture )
    {
        // make sure neither is null
        if ( p_bodyfixture == null) throw new NullPointerException( Messages.getString( "dynamics.body.addNullFixture" ) );
        // add the shape and mass to the respective lists
        this.m_fixtures.add( p_bodyfixture );

        // todo add the fixture to the broadphase

        // return this body to facilitate chaining
        return this;
    }

    @Override
    public BodyFixture addFixture( final Convex p_convex )
    {
        return this.addFixture( p_convex, BodyFixture.DEFAULT_DENSITY, BodyFixture.DEFAULT_FRICTION, BodyFixture.DEFAULT_RESTITUTION );
    }

    /**
     * Creates a {@link BodyFixture} for the given {@link Convex},
     * adds it to the {@link CBoundingBox}, and returns it for configuration.
     *
     * @param p_convex the {@link Convex} to add to the {@link CBoundingBox}
     * @param p_density the density of the shape in kg/m<sup>2</sup>; in the range (0.0, &infin;]
     * @param p_friction the coefficient of friction; in the range [0.0, &infin;]
     * @param p_restitution the coefficient of restitution; in the range [0.0, &infin;]
     * @return {@link BodyFixture} the fixture created using the given shape and added to the {@link CBoundingBox}
     * @throws NullPointerException if convex is null
     * @throws IllegalArgumentException if density is less than or equal to zero; if friction or restitution is less than zero
     */
    public BodyFixture addFixture( final Convex p_convex, final double p_density, final double p_friction, final double p_restitution)
    {
        // make sure the convex shape is not null
        if ( p_convex == null ) throw new NullPointerException( Messages.getString( "dynamics.body.addNullShape" ) );
        // create the fixture
        final BodyFixture l_fixture = new BodyFixture( p_convex );
        // set the properties
        l_fixture.setDensity( p_density );
        l_fixture.setFriction( p_friction );
        l_fixture.setRestitution( p_restitution );
        // add the fixture to the body
        this.m_fixtures.add( l_fixture );

        // todo add the fixture to the broadphase

        // return the fixture so the caller can configure it
        return l_fixture;
    }

    @Override
    public BodyFixture getFixture( final int i )
    {
        return null;
    }

    @Override
    public boolean containsFixture( final BodyFixture p_bodyfixture )
    {
        return false;
    }

    @Override
    public BodyFixture getFixture( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public List<BodyFixture> getFixtures( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public boolean removeFixture( final BodyFixture p_bodyfixture )
    {
        return false;
    }

    @Override
    public BodyFixture removeFixture( final int i )
    {
        return null;
    }

    @Override
    public List<BodyFixture> removeAllFixtures()
    {
        return null;
    }

    @Override
    public BodyFixture removeFixture( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public List<BodyFixture> removeFixtures( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public int getFixtureCount()
    {
        return 0;
    }

    @Override
    public List<BodyFixture> getFixtures()
    {
        return null;
    }

    @Override
    public Iterator<BodyFixture> getFixtureIterator()
    {
        return null;
    }

    @Override
    public boolean contains( final Vector2 p_vector2 )
    {
        return false;
    }

    @Override
    public Vector2 getLocalCenter()
    {
        return null;
    }

    @Override
    public Vector2 getWorldCenter()
    {
        return null;
    }

    @Override
    public Vector2 getLocalPoint( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public Vector2 getWorldPoint( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public Vector2 getLocalVector( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public Vector2 getWorldVector( final Vector2 p_vector2 )
    {
        return null;
    }

    @Override
    public double getRotationDiscRadius()
    {
        return 0;
    }

    @Override
    public Transform getTransform()
    {
        return null;
    }

    @Override
    public void setTransform( final Transform p_transform )
    {

    }

    @Override
    public void rotateAboutCenter( final double v )
    {

    }

    @Override
    public void translateToOrigin()
    {

    }

    @Override
    public void setUserData( final Object p_object )
    {

    }

    @Override
    public Object getUserData()
    {
        return null;
    }

    @Override
    public void rotate( final double v )
    {

    }

    @Override
    public void rotate( final double p_v1, final Vector2 p_vector2 )
    {

    }

    @Override
    public void rotate( final double p_v1, final double p_v2, final double p_v3 )
    {

    }

    @Override
    public void shift( final Vector2 p_vector2 )
    {

    }

    @Override
    public void translate( final double p_v1, final double p_v2 )
    {

    }

    @Override
    public void translate( final Vector2 p_vector2 )
    {

    }
}

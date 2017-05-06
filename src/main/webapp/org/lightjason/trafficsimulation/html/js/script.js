/*
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
 */
"use strict";

jQuery(function() {

    // initialize UI
    jQuery(".tabs" ).tabs();
    jQuery(".selectable").selectable({
        autoRefresh: true,
        selected: function(po_event, po_ui) {
            jQuery(po_ui.selected).addClass("ui-selected").siblings().removeClass("ui-selected");
        },
        stop: function() {
            jQuery( ".ui-selected", this ).each(function() {

                Simulation.ajax( "/agent/" + jQuery( this ).text() + "/view" )
                .done( function(po) {

                    var lo_beliefs = jQuery("<ul></ul>");
                    po.belief.forEach( function(i){ lo_beliefs.append( jQuery("<li></li>").text( Simulation.literal(i) ) ); });
                    jQuery("#agentdetail_belief").empty().append( lo_beliefs );

                    var lo_plans = jQuery("<ul></ul>");
                    po.plan.forEach( function(i){ lo_plans.append( jQuery("<li></li>").text( i.trigger + Simulation.literal(i.literal) + " [Success: " + i.success + "/ Fail: " + i.fail + "]" ) ); });
                    jQuery("#agentdetail_plan").empty().append( lo_plans );

                    jQuery("#agentdetail_main")
                        .empty()
                        .append(
                            jQuery( "<ul></ul>" )
                                .append( "<li><strong>ID:</strong> " + po.id + "</li>" )
                                .append( "<li><strong>Sleeping Cycles:</strong> " + po.sleeping + "</li>" )
                                .append( "<li><strong>Cycle:</strong> " + po.cycle + "</li>" )
                        );

                    console.log(po);
                });

            });
        }
    });

    // read agent list
    Simulation.ajax( "/agent/list" )
    .done( function(po) {
        po.forEach( function(pc) {

            jQuery("#agentlist").append(
                jQuery("<li></li>")
                .addClass("ui-widget-content")
                .text(pc)
            );
        } );
    });

    // initialize game-engine
    new Phaser.Game(
        800, 600, Phaser.AUTO, "screen",
        { create : function() {
            /*
            this.plugins.add( new Phaser.Plugin.Isometric(this) );
            this.world.setBounds(0, 0, 2048, 1024);
            this.physics.startSystem(Phaser.Plugin.Isometric.ISOARCADE);
            this.iso.anchor.setTo(0.5, 0);
            */
            /*
            this.add.text(
                this.world.centerX-300, 0,
                "Mini-Traffic Simulation",
                { font: "65px Arial", fill: "#ff0044", align: "center" }
            );
            */

        }
    });

});

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
        800, 600, Phaser.CANVAS, "screen",
        { create : function() {

            this.add.text(
                this.world.centerX-300, 0,
                "Mini-Traffic Simulation",
                { font: "65px Arial", fill: "#ff0044", align: "center" }
            );

        }
    });

});

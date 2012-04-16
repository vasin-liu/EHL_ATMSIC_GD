/*
 * simple sliding menu using jQuery and Interface - http://www.getintothis.com
 * 
 * note: this library depends on jquery (http://www.jquery.com) and
 * interface (http://interface.eyecon.ro)
 *
 * Copyright (c) 2006 Ramin Bozorgzadeh
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LECENSE.txt) linceses.
 */

$.fn.rb_menu = function(options) {
    var self = this;

    self.options = {
        // transitions: easein, easeout, easeboth, bouncein, bounceout,
        //              bounceboth, elasticin, elasticout, elasticboth
        transition:    'bounceout',
        // trigger events: mouseover, mousedown, mouseup, click, dblclick
        triggerEvent:  'mouseover',
        // number of ms to delay before hiding menu (on page load)
        loadHideDelay : 1000,
        // number of ms to delay before hiding menu (on mouseout)
        blurHideDelay:  500,
        // number of ms for transition effect
        effectDuration: 1000,
        // hide the menu when the page loads
        hideOnLoad: true,
        // automatically hide menu when mouse leaves area
        autoHide: true
    }

    // make sure to check if options are given!
    if(options) {
        $.extend(self.options, options);
    }

    return this.each(function() {
        var menu = $(this).find('.rb_menu');
        var toggle = $(this).find('.rb_toggle span');

        if(self.options.hideOnLoad) {
            // let's hide the menu when the page is loading
            // after {loadHideDelay} milliseconds
            setTimeout( function() {
                menu.hide();

                // add 'hover' class to trigger for css styling
                toggle.hover( function() {
                    $(this).addClass('hover');
                }, function() {
                    $(this).removeClass('hover');
                });
            }, self.options.loadHideDelay);
        }

        // bind event defined by {triggerEvent} to the trigger
        // to open and close the menu
        toggle.bind(self.options.triggerEvent, function() {
            // if the trigger event is click or dblclick, we want
            // to close the menu if its open
            if((self.options.triggerEvent == 'click' || self.options.triggerEvent == 'dblclick') && !self.closed) {
                menu.hide();
            } else {
                menu.show();
            }
        });

        menu.hide = function() {
            if(this.css('display') == 'block' && !self.closed) {
                this.SlideOutLeft(
                    self.options.effectDuration,
                    function() {
                        self.closed = true;
                        self.unbind();
                    },
                    self.options.transition
                );
            }
        }

        menu.show = function() {
            if(this.css('display') == 'none' && self.closed) {
                this.SlideInLeft(
                    self.options.effectDuration,
                    function() {
                        self.closed = false;
                        if(self.options.autoHide) {
                            self.hover(function() {
                                clearTimeout(self.timeout);
                            }, function() {
                                self.timeout = setTimeout( function() {
                                    menu.hide();
                                }, self.options.blurHideDelay);
                            });
                        }
                    },
                    self.options.transition
                );
            }
        }
    });
};
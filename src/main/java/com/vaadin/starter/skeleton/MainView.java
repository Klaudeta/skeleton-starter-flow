package com.vaadin.starter.skeleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServlet;

/**
 * The main view contains a button and a click listener.
 */
@Route
@Push
public class MainView extends VerticalLayout {

    public MainView() {
        Button button = new Button("Click me",
                event -> {
					new Thread(() ->  {
			        	try {
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	
			        	getUI().ifPresent(ui -> {
			        		ui.access(() -> {
			        			Notification.show("Pushed!");
			        		} );
			        	});
			        	
			        	
			        }).start();;
				});
  
        
        add(button);
        
        
       
    }
    
//    @WebServlet(name="frontendServlet", urlPatterns= { "/frontend/*"}, asyncSupported=true)
//    public static class FrontendServlet extends VaadinServlet{
//    	
//    }
    
    @WebServlet(name="customServlet", urlPatterns= { "/vaadin/*", "/frontend/*"}, asyncSupported=true)
    public static class CustomVaadinServlet extends VaadinServlet{
    	
    	@Override
    	public void init() throws ServletException {
    		// TODO Auto-generated method stub
    		super.init();
    	}
    }
}


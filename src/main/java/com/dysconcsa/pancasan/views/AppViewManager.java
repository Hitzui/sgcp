package com.dysconcsa.pancasan.views;

import com.dysconcsa.pancasan.views.caja.CajasPresenter;
import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.afterburner.AppViewRegistry;
import com.gluonhq.charm.glisten.afterburner.Utils;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.dysconcsa.pancasan.PancasanApplication;
import javafx.scene.image.Image;
import java.util.Locale;
import java.util.Objects;

import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.HOME_VIEW;
import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.SHOW_IN_DRAWER;
import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.SKIP_VIEW_STACK;

public class AppViewManager {

    public static final AppViewRegistry REGISTRY = new AppViewRegistry();

    public static final AppView PRIMARY_VIEW = view("Primary", PrimaryPresenter.class, MaterialDesignIcon.HOME, SHOW_IN_DRAWER, HOME_VIEW, SKIP_VIEW_STACK);
    public static final AppView SECONDARY_VIEW = view("Secondary", SecondaryPresenter.class, MaterialDesignIcon.DASHBOARD, SHOW_IN_DRAWER);
    public static final AppView CAJA_VIEW = view("Caja", CajasPresenter.class, MaterialDesignIcon.ADD_BOX, SHOW_IN_DRAWER);

    private static AppView view(String title, Class<?> presenterClass, MaterialDesignIcon menuIcon, AppView.Flag... flags ) {
        return REGISTRY.createView(name(presenterClass), title, presenterClass, menuIcon, flags);
    }

    private static String name(Class<?> presenterClass) {
        return presenterClass.getSimpleName().toUpperCase(Locale.ROOT).replace("PRESENTER", "");
    }
    
    public static void registerViewsAndDrawer() {
        for (AppView view : REGISTRY.getViews()) {
            view.registerView();
        }

        NavigationDrawer.Header header = new NavigationDrawer.Header("Gluon Application",
                "Multi View Project",
                new Avatar(21, new Image(Objects.requireNonNull(PancasanApplication.class.getResourceAsStream("/icon.png")))));

        Utils.buildDrawer(AppManager.getInstance().getDrawer(), header, REGISTRY.getViews()); 
    }
}

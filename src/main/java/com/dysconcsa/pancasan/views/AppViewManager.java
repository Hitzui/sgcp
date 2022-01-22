package com.dysconcsa.pancasan.views;

import com.dysconcsa.pancasan.PancasanApplication;
import com.dysconcsa.pancasan.views.caja.CajasPresenter;
import com.dysconcsa.pancasan.views.rubro.RubroPresenter;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.afterburner.AppViewRegistry;
import com.gluonhq.charm.glisten.afterburner.Utils;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.image.Image;

import java.util.Locale;
import java.util.Objects;

import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.*;

public class AppViewManager {

    public static final AppViewRegistry REGISTRY = new AppViewRegistry();

    public static final AppView PRIMARY_VIEW = view("Primary", PrimaryPresenter.class, MaterialDesignIcon.HOME, SHOW_IN_DRAWER, HOME_VIEW, SKIP_VIEW_STACK);
    //public static final AppView SECONDARY_VIEW = view("Secondary", SecondaryPresenter.class, MaterialDesignIcon.DASHBOARD, SHOW_IN_DRAWER);
    public static final AppView CAJA_VIEW = view("Caja", CajasPresenter.class, MaterialDesignIcon.ADD_BOX);
    public static final AppView RUBRO_VIEW = view("Rubro", RubroPresenter.class, MaterialDesignIcon.SUBJECT);

    private static AppView view(String title, Class<?> presenterClass, MaterialDesignIcon menuIcon, AppView.Flag... flags) {
        return REGISTRY.createView(name(presenterClass), title, presenterClass, menuIcon, flags);
    }

    private static String name(Class<?> presenterClass) {
        return presenterClass.getSimpleName().toUpperCase(Locale.ROOT).replace("PRESENTER", "");
    }

    public static void registerViewsAndDrawer() {
        for (AppView view : REGISTRY.getViews()) {
            view.registerView();
        }
        var drawer = AppManager.getInstance().getDrawer();
        NavigationDrawer.Header header = new NavigationDrawer.Header("SGC",
                "Taller Pancasan",
                new Avatar(21, new Image(Objects.requireNonNull(PancasanApplication.class.getResourceAsStream("/icon.png")))));
        // create items
        var backItem = new NavigationDrawer.Item("Volver", MaterialDesignIcon.ARROW_BACK.graphic());
        backItem.setId("back");
        var menuCaja = new NavigationDrawer.Item("Manejo caja", MaterialDesignIcon.MENU.graphic());
        menuCaja.setId("menu_caja");
        drawer.getItems().add(menuCaja);
        drawer.selectedItemProperty().addListener((obs, ov, nv) -> {
            if ("menu_caja".equals(nv.getId())) {
                ((NavigationDrawer.Item) nv).setAutoClose(false);
                drawer.getItems().clear();
                drawer.getItems().addAll(backItem, CAJA_VIEW.getMenuItem(), RUBRO_VIEW.getMenuItem());
            }
            if ("back".equals(nv.getId())) {
                ((NavigationDrawer.Item) nv).setAutoClose(false);
                drawer.getItems().clear();
                drawer.getItems().add(PRIMARY_VIEW.getMenuItem());
                drawer.getItems().add(menuCaja);
                if (Platform.isDesktop()) {
                    NavigationDrawer.Item quitItem = new NavigationDrawer.Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
                    quitItem.selectedProperty().addListener((obs1, ov1, nv1) -> {
                        if (nv1) {
                            //LifecycleService.create().ifPresent(LifecycleService::shutdown);
                            javafx.application.Platform.exit();
                        }

                    });
                    drawer.getItems().add(quitItem);
                }
            }
        });

        Utils.buildDrawer(drawer, header, REGISTRY.getViews());
    }
}

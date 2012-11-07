package org.miernik.jfxlib.presenter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.jodah.concurrentunit.junit.ConcurrentTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.miernik.jfxlib.Service;

public class BasePresenterTest extends ConcurrentTestCase {

	final static Logger logger = Logger.getLogger(BasePresenterTest.class);

	boolean result;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@BeforeClass
	public static void initJFX() throws Exception {
		new JFXPanel();
	}

	@Test
	public void testOnShow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnShow");
				result = false;
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {

					@Override
					protected void onShow() {
						result = true;
					}
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));

				threadAssertFalse(stage.isShowing());
				threadAssertFalse(result);

				logger.debug("invoke show on stage object");
				stage.show();
				threadAssertTrue(stage.isShowing());
				threadAssertTrue(result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testOnShow2() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnShow2");
				result = false;
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {
				};
				AnchorPane view = new AnchorPane();
				p.setView(view);
				stage.setScene(new Scene(p.getView()));
				stage.show();
				threadAssertTrue(stage.isShowing());
				threadAssertFalse(result);
				
				final BasePresenter<Service> p2 = new BasePresenter<Service>() {

					@Override
					protected void onShow() {
						result = true;
					}
				};
				p2.setView(new AnchorPane());
				view.getChildren().add(p2.getView());
				threadAssertTrue(result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testOnHide() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnHide");
				result = false;
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {

					@Override
					protected void onHide() {
						result = true;
					}
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));

				logger.debug("invoke show on stage object");
				stage.show();
				threadAssertTrue(stage.isShowing());
				threadAssertFalse(result);

				logger.debug("invoke hide on stage object");
				stage.hide();
				threadAssertFalse(stage.isShowing());
				threadAssertTrue(result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testGetWindow() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testGetWindow");
				final Stage stage = new Stage();
				final BasePresenter<Service> p = new BasePresenter<Service>() {
				};
				p.setView(new AnchorPane());
				stage.setScene(new Scene(p.getView()));

				threadAssertEquals(stage, p.getWindow());

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testGetScene() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testGetScene");
				final BasePresenter<Service> p = new BasePresenter<Service>() {
				};
				p.setView(new AnchorPane());
				Scene scene = new Scene(p.getView());

				threadAssertEquals(scene, p.getScene());

				resume();
			}
		});
		threadWait(1000);
	}
	
	class TestBasePresenter extends BasePresenter<Service> {
		public int result = 0;
	}	

	@Test
	public void testOnInit() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnInit");
				final Stage stage = new Stage();
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onInit() {
						result++;
						threadAssertNotNull(getWindow());
						threadAssertNotNull(getScene());
						threadAssertNotNull(getView());
					}
				};
				threadAssertEquals(0, p.result);
				Parent view = new AnchorPane(); 
				p.setView(view);
				stage.setScene(new Scene(p.getView()));
				p.setView(view);
				threadAssertEquals(1, p.result);
				

				resume();
			}
		});
		threadWait(1000);
	}
	
	@Test
	public void testOnInitSubView() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnInitSubView");
				final Stage stage = new Stage();
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onInit() {
						result++;
					}
				};
				AnchorPane view = new AnchorPane();
				p.setView(view);
				stage.setScene(new Scene(p.getView()));
				final TestBasePresenter p2 = new TestBasePresenter() {
					@Override
					protected void onInit() {
						result++;
					}
				};
				threadAssertEquals(0, p2.result);
				p2.setView(new AnchorPane());
				view.getChildren().add(p2.getView());
				threadAssertEquals(1, p2.result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testOnInitSubViewExchange() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnInitSubViewExchange");
				final Stage stage = new Stage();
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onInit() {
						result++;
					}
				};
				AnchorPane view = new AnchorPane();
				p.setView(view);
				stage.setScene(new Scene(p.getView()));
				final TestBasePresenter p2 = new TestBasePresenter() {
					@Override
					protected void onInit() {
						logger.debug("exec onInit - p2");
						result++;
					}
				};
				p2.setView(new AnchorPane());
				final TestBasePresenter p3 = new TestBasePresenter() {
					@Override
					protected void onInit() {
						logger.debug("exec onInit - p3");
						result++;
					}
				};
				p3.setView(new AnchorPane());
				threadAssertEquals(0, p2.result);
				view.getChildren().add(p2.getView());
				threadAssertEquals(1, p2.result);
				
				threadAssertEquals(0, p3.result);
				view.getChildren().clear();
				view.getChildren().add(p3.getView());
				threadAssertEquals(1, p3.result);

				view.getChildren().clear();
				view.getChildren().add(p2.getView());
				threadAssertEquals(1, p2.result);

				view.getChildren().clear();
				view.getChildren().add(p3.getView());
				threadAssertEquals(1, p3.result);

				resume();
			}
		});
		threadWait(1000);
	}
	
	@Test
	public void testCheckOnInit() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testCheckOnInit");
				AnchorPane view = new AnchorPane();
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onInit() {
						result++;
					}
				};
				p.setView(view);
				threadAssertEquals(0, p.result);
				p.setWindow(null);
				p.setScene(null);
				threadAssertEquals(0, p.result);
				p.checkOnInit();
				threadAssertEquals(0, p.result);

				resume();
			}
		});
		threadWait(1000);		
	}

	@Test
	public void testOnShowSubView() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnShowMoreViews");
				final Stage stage = new Stage();
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onShow() {
						result++;
					}
				};
				AnchorPane view = new AnchorPane();
				p.setView(view);
				stage.setScene(new Scene(p.getView()));
				final TestBasePresenter p2 = new TestBasePresenter() {
					@Override
					protected void onShow() {
						result++;
					}
				};
				p2.setView(new AnchorPane());
				view.getChildren().add(p2.getView());
				threadAssertEquals(0, p.result);
				threadAssertEquals(0, p2.result);
				stage.show();
				threadAssertEquals(1, p.result);
				threadAssertEquals(1, p2.result);

				resume();
			}
		});
		threadWait(1000);
	}

	@Test
	public void testOnHideSubView() throws Throwable {
		Platform.runLater(new Runnable() {
			public void run() {
				logger.debug("TEST: testOnHideSubView");
				final Stage stage = new Stage();
				final TestBasePresenter p = new TestBasePresenter() {
					@Override
					protected void onHide() {
						result++;
					}
				};
				AnchorPane view = new AnchorPane();
				p.setView(view);
				stage.setScene(new Scene(p.getView()));
				final TestBasePresenter p2 = new TestBasePresenter() {
					@Override
					protected void onHide() {
						result++;
					}
				};
				p2.setView(new AnchorPane());
				view.getChildren().add(p2.getView());
				threadAssertEquals(0, p.result);
				threadAssertEquals(0, p2.result);
				stage.show();
				stage.hide();
				threadAssertEquals(1, p.result);
				threadAssertEquals(1, p2.result);

				resume();
			}
		});
		threadWait(1000);
	}
}

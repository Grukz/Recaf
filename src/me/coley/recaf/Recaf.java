package me.coley.recaf;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;

import org.objectweb.asm.tree.ClassNode;

import me.coley.recaf.asm.AsmUtil;
import me.coley.recaf.asm.JarData;
import me.coley.recaf.ui.FileChoosers;
import me.coley.recaf.ui.Gui;

public class Recaf {
	private static Recaf instance;
	public Gui window;
	public File currentJar;
	public JarData jarData;
	public FileChoosers fileChoosers;
	public Options options;
	public AsmUtil asm;

	public Recaf() {
		instance = this;
		fileChoosers = new FileChoosers();
		options = new Options();
		options.load();
		asm = new AsmUtil();
	}

	public void openFile(File file) throws IOException {
		this.currentJar = file;
		this.jarData = new JarData(file);
		this.window.updateTree();
		this.window.getFrame().setTitle("Recaf: " + file.getName());
	}

	public void saveFile(File file) throws IOException {
		this.jarData.save(file);
	}

	public void selectClass(ClassNode node) {
		this.window.addClassView(node);
	}

	public void showGui() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Gui();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Recaf getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Recaf program = new Recaf();
		program.showGui();

	}
}

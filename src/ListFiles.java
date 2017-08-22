import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;

public class ListFiles {
	public static String[] getFiles() {
		File dir = new File(System.getProperty("user.dir"));
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".mcr");
			}
		});
		Arrays.sort(files, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				Path p1 = Paths.get(((File) o1).getAbsolutePath());
				BasicFileAttributes attr = null;
				try {
					attr = Files.readAttributes(p1, BasicFileAttributes.class);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Path p2 = Paths.get(((File) o2).getAbsolutePath());
				BasicFileAttributes attr2 = null;
				try {
					attr2 = Files.readAttributes(p2, BasicFileAttributes.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (attr.creationTime().toMillis() < attr2.creationTime()
						.toMillis()) {
					return -1;
				} else if (attr.creationTime().toMillis() > attr2
						.creationTime().toMillis()) {
					return +1;
				} else {
					return 0;
				}
			}
		});

		String[] f = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			String x = files[i].toString();
			f[files.length - 1 - i] = x.substring(x.lastIndexOf("/") + 1)
					.substring(x.lastIndexOf("\\") + 1);
		}
		return f;
	}
}

package scripts.dax.api_lib.models;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public interface Requirement {
	boolean satisfies();
}
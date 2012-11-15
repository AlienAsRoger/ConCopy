package hu.velorum.ConCopy.ui;

import hu.velorum.ConCopy.backend.interfaces.TaskUpdateInterface;

import java.util.List;

/**
 * ContactItemGetFace class
 *
 * @author alien_roger
 * @created at: 22.08.12 8:08
 */
public interface ContactItemGetFace<E,T> extends TaskUpdateInterface<T> {

	void updateContacts(List<E> itemsList);
}

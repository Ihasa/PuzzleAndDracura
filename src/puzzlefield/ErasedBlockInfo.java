package puzzlefield;

import java.util.ArrayList;
import java.util.List;

public class ErasedBlockInfo {
	public BlockColor color;
	public int blockNum;
	private List<FieldIndex> indexes;
	public ErasedBlockInfo(BlockColor c){
		color = c;
		//blockNum = num;
		indexes = new ArrayList<FieldIndex>();
	}
	public FieldIndex getIndex(int i){
		if(i >= blockNum) return null;
		return indexes.get(i);
	}
	public void addIndex(FieldIndex idx){
		indexes.add(idx);
		blockNum = indexes.size();
	}
}

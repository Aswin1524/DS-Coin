package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
    CRF obj = new CRF(64);
    BlockChain_Honest current=this;
    TransactionBlock t = newBlock;
    String s = "";
    if(current.lastBlock==null){
      current.lastBlock=t;
      t.previous=null;
      for(long i=1000000000L; i<=9999999999L; i++){
        if(obj.Fn(current.start_string + "#" + t.trsummary + "#" + String.valueOf(i)).substring(0,4).equals("0000")){
          t.nonce=String.valueOf(i);
          t.dgst=obj.Fn(current.start_string + "#" + t.trsummary + "#" + String.valueOf(i));
          break;
        }
      }     
    }
    else{
      t.previous=current.lastBlock;
      current.lastBlock=t;
      for(long i=1000000000L; i<=9999999999L; i++){
        if(obj.Fn(t.previous.dgst + "#" + t.trsummary + "#" + String.valueOf(i)).substring(0,4).equals("0000")){
          t.nonce=String.valueOf(i);
          t.dgst=obj.Fn(t.previous.dgst + "#" + t.trsummary + "#" + String.valueOf(i));
          break;
        }
      }     
    }
  }
  
}
package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {

  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

  TransactionBlock(Transaction[] t) {
    this.trarray=t.clone();
    this.previous=null;
    MerkleTree MT = new MerkleTree();
    MT.Build(trarray);
    this.Tree=MT;
    this.trsummary=MT.rootnode.val;
    this.dgst=null;
  }

  public boolean checkTransaction (Transaction t) {
    TransactionBlock sblock = t.coinsrc_block;
    boolean b = false;
    if(sblock==null){
      b=true;
    }
    else{
      Transaction[] tary = sblock.trarray;
      for(int i=0; i<tary.length; i++){
        if(tary[i].coinID.equals(t.coinID) && tary[i].Destination.equals(t.Source)){
          b=true;
          break;
        }
      }
    }
    TransactionBlock current=this;
    if(b==true){
      while(current.previous!=null && !current.previous.equals(sblock)){
        current=current.previous;
        Transaction[] tary1 = current.trarray;
        for(int j=0; j<tary1.length; j++){
          if(tary1[j].coinID.equals(t.coinID)){
            b=false;
            break;
          }
        }
      }
    }
    return b;
  }
  
}
package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class BlockChain_Malicious {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;

  public static boolean checkTransactionBlock (TransactionBlock tB) {
    CRF obj = new CRF(64);
    boolean b = false;
    if(tB.previous==null){
      if(tB.dgst.substring(0,4).equals("0000") && tB.dgst.equals(obj.Fn("DSCoin" + "#" + tB.trsummary + "#" + tB.nonce))){
        b = true;
      }
    }
    else{
      if(tB.dgst.substring(0,4).equals("0000") && tB.dgst.equals(obj.Fn(tB.previous.dgst + "#" + tB.trsummary + "#" + tB.nonce))){
        b = true;
      }
    }
    if(b==true){
      MerkleTree MT = new MerkleTree();
      MT.Build(tB.trarray);
      if(!tB.trsummary.equals(MT.rootnode.val)){
        b=false;
      }
    }
    if(b==true){
      for(int i=0; i<tB.trarray.length; i++){
        if(tB.checkTransaction(tB.trarray[i])==false){
          b=false;
          break;
        }
      }
    }
    return b;
  }

  public TransactionBlock FindLongestValidChain () {
    BlockChain_Malicious current = this;
    TransactionBlock[] ltlist = current.lastBlocksList;
    int a=0; boolean b1=true;
    while(a<100 && b1==true){
      if(ltlist[a]!=null){
        a=a+1;
      }
      else{b1=false;}
    }
    TransactionBlock[] lvblock = new TransactionBlock[a];
    int[] len = new int[a];
    for(int b=0; b<a; b++){
      lvblock[b]=ltlist[b];
      len[b]=0;
    }
    for(int i=0; i<a; i++){
      TransactionBlock curr = ltlist[i];
      while(curr!=null){
        if(checkTransactionBlock(curr)==false){
          len[i]=0;
          lvblock[i]=curr.previous;
        }
        else{
          len[i]=len[i]+1;
        }
        curr=curr.previous;
      }
    }
    int n=0;
    for(int j=0; j<len.length; j++){
      if(len[n]<len[j]){
        n=j;
      }
    }
    return lvblock[n];
  }

  public void InsertBlock_Malicious (TransactionBlock newBlock) {
    CRF obj = new CRF(64);
    BlockChain_Malicious current = this;
    TransactionBlock[] ltlist = current.lastBlocksList;
    TransactionBlock curr = current.FindLongestValidChain();
    TransactionBlock t = newBlock;
    t.previous=curr;
    int a=0; boolean b1=true;
    while(a<100 && b1==true){
      if(ltlist[a]!=null){
        a=a+1;
      }
      else{b1=false;}
    }
    boolean b = false;
    int n=0;
    for(int i=0; i<a; i++){
      if(curr.equals(ltlist[i])){
        b=true;
        n=i;
        break;
      }
    }
    if(b==true){
      ltlist[n]=t;
    }
    else{
      ltlist[a]=t;
    }
    for(long i=1000000000L; i<=9999999999L; i++){
        if(obj.Fn(t.previous.dgst + "#" + t.trsummary + "#" + String.valueOf(i)).substring(0,4).equals("0000")){
          t.nonce=String.valueOf(i);
          t.dgst=obj.Fn(t.previous.dgst + "#" + t.trsummary + "#" + String.valueOf(i));
          break;
        }
    }     
  }
  
}
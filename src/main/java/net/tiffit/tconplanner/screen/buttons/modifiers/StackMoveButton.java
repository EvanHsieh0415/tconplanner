package net.tiffit.tconplanner.screen.buttons.modifiers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.tiffit.tconplanner.screen.PlannerScreen;
import net.tiffit.tconplanner.screen.buttons.PaginatedPanel;
import net.tiffit.tconplanner.util.TranslationUtil;

public class StackMoveButton extends Button {
    private static final ITextComponent MOVE_UP = TranslationUtil.createComponent("modifierstack.moveup");
    private static final ITextComponent MOVE_DOWN = TranslationUtil.createComponent("modifierstack.movedown");
    private final PaginatedPanel<ModifierStackButton> scrollPanel;
    private final PlannerScreen parent;
    private final boolean moveUp;

    public StackMoveButton(int x, int y, boolean moveUp, PaginatedPanel<ModifierStackButton> scrollPanel, PlannerScreen parent) {
        super(x, y, 18, 10, new StringTextComponent(""), e -> {});
        this.parent = parent;
        this.moveUp = moveUp;
        this.scrollPanel = scrollPanel;
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float p_230431_4_) {
        RenderSystem.enableBlend();
        PlannerScreen.bindTexture();
        parent.blit(stack, x, y, 214, 145 + (moveUp ? 0 : height), width, height);
        if(isHovered){
            renderToolTip(stack, mouseX, mouseY);
        }
    }

    @Override
    public void renderToolTip(MatrixStack stack, int mouseX, int mouseY) {
        parent.postRenderTasks.add(() -> parent.renderTooltip(stack, moveUp ? MOVE_UP : MOVE_DOWN, mouseX, mouseY));
    }

    @Override
    public void onPress() {
        if(moveUp){
            if(parent.selectedModifierStackIndex > 0){
                parent.modifierStack.moveDown(parent.selectedModifierStackIndex - 1);
                parent.selectedModifierStackIndex--;
                scrollPanel.makeVisible(parent.selectedModifierStackIndex, false);
                parent.refresh();
            }
        }else{
            if(parent.selectedModifierStackIndex < parent.modifierStack.getStack().size() - 1){
                parent.modifierStack.moveDown(parent.selectedModifierStackIndex);
                parent.selectedModifierStackIndex++;
                scrollPanel.makeVisible(parent.selectedModifierStackIndex, false);
                parent.refresh();
            }
        }
    }
}
